package com.example.samtransbusstopdisplay;

import java.time.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class EstimatedTimeCalculator {
	public static LinkedHashMap<Integer, List<String>> getEstimatedTime() {
		LinkedHashMap<Integer, List<String>> timeValues = JsonParser.getEstimatedArrivalTime(SamTransAPI.SamTransAPICall());
		LinkedHashMap<Integer, List<String>> outputTimes = new LinkedHashMap<>();

		// uses the current iso time and estimated iso time to calculate eta in minutes
		for (Map.Entry<Integer, List<String>> timeValue : timeValues.entrySet()) {
			Integer lineNumber = timeValue.getKey();
			List<String> timeISOList = timeValue.getValue();

			ArrayList<String> timeList = new ArrayList<>(); // Create a new list for each line number

			for (String time : timeISOList) {
				Instant utcInstant = Instant.parse(time);
				ZoneId timezone = ZoneId.of("America/Los_Angeles");
				ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(utcInstant, timezone);
				LocalDateTime pstTime = zonedDateTime.toLocalDateTime();
				LocalDateTime currentDateTime = LocalDateTime.now();
				Duration timeDifference = Duration.between(currentDateTime, pstTime);
				String minutes = GetMinutes(timeDifference);
				timeList.add(minutes);
			}

			outputTimes.put(lineNumber, timeList); // Put the list corresponding to each line number
		}
		return outputTimes;
	}
	private static String GetMinutes(Duration duration) {
		//If the time passed without the bus arriving, set the minutes to 0
		if (duration.isNegative())
			return "0";
		long minutes = duration.toMinutes() % 60;
		String minutesString = String.format("%02d", minutes);

		// If the minutesString has a leading 0, replace it with a space
		if (minutesString.startsWith("0")) {
			minutesString = " " + minutesString.substring(1);
		}
		return minutesString;
	}
}

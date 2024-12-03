package com.example.samtransbusstopdisplay;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class JsonParser {
	public static LinkedHashMap<Integer, List<String>> getEstimatedArrivalTime(String originalString) {
		//originalString = "{\"ServiceDelivery\":{\"ResponseTimestamp\":\"2023-11-16T05:26:09Z\",\"ProducerRef\":\"SM\",\"Status\":true,\"StopMonitoringDelivery\":{\"version\":\"1.4\",\"ResponseTimestamp\":\"2023-11-16T05:26:09Z\",\"Status\":true,\"MonitoredStopVisit\":[{\"RecordedAtTime\":\"2023-11-16T05:25:47Z\",\"MonitoringRef\":\"341081\",\"MonitoredVehicleJourney\":{\"LineRef\":\"250\",\"DirectionRef\":\"W\",\"FramedVehicleJourneyRef\":{\"DataFrameRef\":\"2023-11-15\",\"DatedVehicleJourneyRef\":\"12315173-144-Blocks-Weekday-50\"},\"PublishedLineName\":\"San Mateo Caltrain - College of San Mateo\",\"OperatorRef\":\"SM\",\"OriginRef\":\"341290\",\"OriginName\":\"S San Mateo Dr & 2nd Ave\",\"DestinationRef\":\"341081\",\"DestinationName\":\"College of San Mateo\",\"Monitored\":true,\"InCongestion\":null,\"VehicleLocation\":{\"Longitude\":\"-122.303947\",\"Latitude\":\"37.5339394\"},\"Bearing\":\"255.0000000000\",\"Occupancy\":null,\"VehicleRef\":\"649\",\"MonitoredCall\":{\"StopPointRef\":\"341081\",\"StopPointName\":\"CSM Transit Ctr\",\"VehicleLocationAtStop\":\"\",\"VehicleAtStop\":\"\",\"DestinationDisplay\":\"College of San Mateo\",\"AimedArrivalTime\":\"2023-11-16T05:35:00Z\",\"ExpectedArrivalTime\":\"2023-11-16T08:30:44Z\",\"AimedDepartureTime\":\"2023-11-16T05:35:00Z\",\"ExpectedDepartureTime\":null,\"Distances\":\"\"}}}]}}}";

		ArrayList<Integer> lineList = new ArrayList<>();
		LinkedHashMap<Integer, List<String>> timeMap = new LinkedHashMap<>();

		// converting json string into a json object
		int i = originalString.indexOf("{");
		originalString = originalString.substring(i);
		JSONObject jsonObject = new JSONObject(originalString);

		// checks to see if there are any buses being tracked
		boolean monitored = jsonObject.getJSONObject("ServiceDelivery").getJSONObject("StopMonitoringDelivery").getBoolean("Status");
		boolean empty = jsonObject.getJSONObject("ServiceDelivery").getJSONObject("StopMonitoringDelivery").getJSONArray("MonitoredStopVisit").isEmpty();

		if (monitored && !empty) {
			for (int j = 0; j < jsonObject.getJSONObject("ServiceDelivery").getJSONObject("StopMonitoringDelivery").getJSONArray("MonitoredStopVisit").length(); j++) {
				boolean containsNull = jsonObject.getJSONObject("ServiceDelivery").getJSONObject("StopMonitoringDelivery").getJSONArray("MonitoredStopVisit").getJSONObject(j).getJSONObject("MonitoredVehicleJourney").getJSONObject("MonitoredCall").isNull("ExpectedArrivalTime");
				String lineNumber = jsonObject.getJSONObject("ServiceDelivery").getJSONObject("StopMonitoringDelivery").getJSONArray("MonitoredStopVisit").getJSONObject(j).getJSONObject("MonitoredVehicleJourney").getString("LineRef");

				if (!containsNull) {
					String temp = jsonObject.getJSONObject("ServiceDelivery").getJSONObject("StopMonitoringDelivery").getJSONArray("MonitoredStopVisit").getJSONObject(j).getJSONObject("MonitoredVehicleJourney").getJSONObject("MonitoredCall").getString("ExpectedArrivalTime");
					lineList.add(Integer.valueOf(lineNumber));
					ArrayList<String> timeList = new ArrayList<>();
					timeList.add(temp);
					timeMap.put(Integer.valueOf(lineNumber), timeList);
				}
			}
		}
		return timeMap;
	}
}

package com.example.samtransbusstopdisplay;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class Main {
	public static void main(String[] args) {
		LinkedHashMap<Integer, List<String>> timeMap= EstimatedTimeCalculator.getEstimatedTime();
		if(timeMap.isEmpty()){
			System.out.println("No Upcoming Tracked Buses");
		}
		for(Map.Entry<Integer, List<String>> entry : timeMap.entrySet()){
			int lineNumber = entry.getKey();
			List<String> timeValues = entry.getValue();
			System.out.println("Line Number: " + lineNumber);
			for (String timeValue : timeValues) {
				System.out.println("ETA: " + timeValue + " mins");
			}
		}
	}
}
package com.example.samtransbusstopdisplay;

import java.util.LinkedHashMap;
import java.util.List;

public class CurrentBuses {
	public static List<String> currentBusTimes250(){
		LinkedHashMap<Integer, List<String>> timeMap= EstimatedTimeCalculator.getEstimatedTime();
		return timeMap.get(250);
	}
	public static List<String> currentBusTimes56(){
		LinkedHashMap<Integer, List<String>> timeMap= EstimatedTimeCalculator.getEstimatedTime();
		return timeMap.get(56);
	}
}

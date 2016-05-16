package statistics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import data.*;
import utils.Globals;

public class StatisticsClassifier {
	private static Map<String, Business> BusinessesMap;
	private static Map<String, Checkin> CheckinsMap;
	
	public static void init() {
		BusinessesMap = new HashMap<String, Business>();
		for (Business business : Globals.BUSINESSES) {
			BusinessesMap.put(business.getBusiness_id(), business);
		}
		
		CheckinsMap = new HashMap<String, Checkin>();
		for (Checkin checkin : Globals.CHECKINS) {
			CheckinsMap.put(checkin.getBusiness_id(), checkin);
		}
	}
	
	public static double getStarsByIsOpen(String businessId) {
		Business business = BusinessesMap.get(businessId);
		
		if(business.isOpen()) {
			return 3.7;
		}
		else {
			return 3.46;
		}
	}

	public static double getStarsByReviewsCount(String businessId) {
		Business business = BusinessesMap.get(businessId);
		
		int reviewsCount = business.getReview_count();
		
		if(reviewsCount <= 100) {
			return 3.67;
		}
		else if(reviewsCount <= 200) {
			return 3.82;
		}
		else if(reviewsCount <= 300) {
			return 3.99;
		}
		else {
			return 4.04;
		}
	}


	public static double getStarsByCheckinsCount(String businessId) {
		int checkinsCount = 0;

		if (CheckinsMap.containsKey(businessId)) {
			for (int checkinForHour : CheckinsMap.get(businessId).getCheckin_info().values()) {
				checkinsCount += checkinForHour;
			}
		}
		
		if(checkinsCount <= 500) {
			return 3.67;
		}
		else if(checkinsCount <= 1000) {
			return 3.82;
		}
		else if(checkinsCount <= 1500) {
			return 3.85;
		}
		else {
			return 3.76;
		}
	}
}

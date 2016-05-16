package statistics;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import data.*;
import utils.Constants;
import utils.Formatter;
import utils.Globals;

public class Dependencies {
	private static Map<String, Checkin> CheckinsMap;
	
	private static double AverageOpenedStars;
	private static double AverageClosedStars;

	private static double AverageReviewsCount;
	private static int MinReviewsCount;
	private static int MaxReviewsCount;

	private static double AverageCheckinsCount;
	private static int MinCheckinsCount;
	private static int MaxCheckinsCount;
	
	private static List<Point> ReviewsCountStarsPoints;
	private static List<Point> CheckinsCountStarsPoints;
	
	private static void init() {
		CheckinsMap = new HashMap<String, Checkin>();
		for(Checkin checkin : Globals.CHECKINS) {
			CheckinsMap.put(checkin.getBusiness_id(), checkin);
		}
		
		AverageOpenedStars = 0.0;
		AverageClosedStars = 0.0;
		
		AverageReviewsCount = 0.0;
		MinReviewsCount = Integer.MAX_VALUE;
		MaxReviewsCount = Integer.MIN_VALUE;

		AverageCheckinsCount = 0.0;
		MinCheckinsCount = Integer.MAX_VALUE;
		MaxCheckinsCount = Integer.MIN_VALUE;
		
		ReviewsCountStarsPoints = new ArrayList<Point>();
		CheckinsCountStarsPoints = new ArrayList<Point>();
	}
	
	private static void calculateAveragesForIsOpen() {
		double openCount = 0;
		double closedCount = 0;
		
		for(Business business : Globals.BUSINESSES) {
			if(business.getStars() > 0) {
				if(business.isOpen()) {
					openCount++;
					AverageOpenedStars += business.getStars();
				}
				else {
					closedCount++;
					AverageClosedStars += business.getStars();
				}
				
			}
		}
		
		AverageOpenedStars /= openCount;
		AverageClosedStars /= closedCount;
	}
	
	private static void calculateAveragesForReviews() {
		for(Business business : Globals.BUSINESSES) {
			if(business.getReview_count() > MaxReviewsCount) {
				MaxReviewsCount = business.getReview_count();
			}
			if(business.getReview_count() < MinReviewsCount) {
				MinReviewsCount = business.getReview_count();
			}
			
			AverageReviewsCount += business.getReview_count();
		}
		
		AverageReviewsCount /= (double) Globals.BUSINESSES.size();
	}

	private static void calculateAveragesForCheckins() {
		for(Business business : Globals.BUSINESSES) {
			int checkinsCount = 0;
			
			if(CheckinsMap.containsKey(business.getBusiness_id())) {
				for(int checkinForHour : CheckinsMap.get(business.getBusiness_id()).getCheckin_info().values()) {
					checkinsCount += checkinForHour;
				}
			}
			
			if(checkinsCount > MaxCheckinsCount) {
				MaxCheckinsCount = checkinsCount;
			}
			if(checkinsCount < MinCheckinsCount) {
				MinCheckinsCount = checkinsCount;
			}
			
			AverageCheckinsCount += checkinsCount;
		}
		
		AverageCheckinsCount /= (double) Globals.BUSINESSES.size();
	}
	
	private static void calculatePoints() throws Exception {
		for(Business business : Globals.BUSINESSES) {
			if(business.getStars() > 0) {
				ReviewsCountStarsPoints.add(new Point(business.getReview_count(), business.getStars()));
				
				int checkinsCount = 0;
				
				if(CheckinsMap.containsKey(business.getBusiness_id())) {
					for(int checkinForHour : CheckinsMap.get(business.getBusiness_id()).getCheckin_info().values()) {
						checkinsCount += checkinForHour;
					}
				}

				CheckinsCountStarsPoints.add(new Point(checkinsCount, business.getStars()));
			}
		}
		
		FileUtils.writeStringToFile(new File(Constants.STATISTICS_REVIEW_POINTS_PATH), Point.listToString(ReviewsCountStarsPoints));
		FileUtils.writeStringToFile(new File(Constants.STATISTICS_CHECKIN_POINTS_PATH), Point.listToString(CheckinsCountStarsPoints));
	}
	
	private static void printResults() {
		System.out.println("Average stars: Open - " + Formatter.formatDouble(AverageOpenedStars) + "; Closed - " + Formatter.formatDouble(AverageClosedStars) + ";" );
		System.out.println("Reviews count: Min - " + MinReviewsCount + "; Avg - " + Formatter.formatDouble(AverageReviewsCount) + "; Max - " + MaxReviewsCount + ";" );
		System.out.println("Checkins count: Min - " + MinCheckinsCount + "; Avg - " + Formatter.formatDouble(AverageCheckinsCount) + "; Max - " + MaxCheckinsCount + ";" );
	}
	
	public static void calculateDependencies() throws Exception {
		init();
		calculateAveragesForIsOpen();
		calculateAveragesForReviews();
		calculateAveragesForCheckins();
		printResults();
		
		calculatePoints();
	}
}

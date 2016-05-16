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

	private static List<Point> ReviewsCountStarsPoints;
	private static List<Point> CheckinsCountStarsPoints;

	private static double Margin0To100ReviewsAvgStars;
	private static double Margin0To100ReviewsCount;

	private static double Margin100To200ReviewsAvgStars;
	private static double Margin100To200ReviewsCount;

	private static double Margin200To300ReviewsAvgStars;
	private static double Margin200To300ReviewsCount;

	private static double Margin300PlusReviewsAvgStars;
	private static double Margin300PlusReviewsCount;

	private static double Margin0To500CheckinsAvgStars;
	private static double Margin0To500CheckinsCount;

	private static double Margin500To1000CheckinsAvgStars;
	private static double Margin500To1000CheckinsCount;

	private static double Margin1000To1500CheckinsAvgStars;
	private static double Margin1000To1500CheckinsCount;

	private static double Margin1500PlusCheckinsAvgStars;
	private static double Margin1500PlusCheckinsCount;

	private static void init() {
		CheckinsMap = new HashMap<String, Checkin>();
		for (Checkin checkin : Globals.CHECKINS) {
			CheckinsMap.put(checkin.getBusiness_id(), checkin);
		}

		AverageOpenedStars = 0.0;
		AverageClosedStars = 0.0;

		ReviewsCountStarsPoints = new ArrayList<Point>();
		CheckinsCountStarsPoints = new ArrayList<Point>();
	}

	private static void calculateAveragesForIsOpen() {
		double openCount = 0;
		double closedCount = 0;

		for (Business business : Globals.BUSINESSES) {
			if (business.getStars() > 0) {
				if (business.isOpen()) {
					openCount++;
					AverageOpenedStars += business.getStars();
				} else {
					closedCount++;
					AverageClosedStars += business.getStars();
				}

			}
		}

		AverageOpenedStars /= openCount;
		AverageClosedStars /= closedCount;
	}

	private static void calculatePoints() throws Exception {
		for (Business business : Globals.BUSINESSES) {
			if (business.getStars() > 0) {
				ReviewsCountStarsPoints.add(new Point(business.getReview_count(), business.getStars()));

				int checkinsCount = 0;

				if (CheckinsMap.containsKey(business.getBusiness_id())) {
					for (int checkinForHour : CheckinsMap.get(business.getBusiness_id()).getCheckin_info().values()) {
						checkinsCount += checkinForHour;
					}
				}

				CheckinsCountStarsPoints.add(new Point(checkinsCount, business.getStars()));
			}
		}

		FileUtils.writeStringToFile(new File(Constants.STATISTICS_REVIEW_POINTS_PATH),
				Point.listToString(ReviewsCountStarsPoints));
		FileUtils.writeStringToFile(new File(Constants.STATISTICS_CHECKIN_POINTS_PATH),
				Point.listToString(CheckinsCountStarsPoints));
	}

	private static void calculateCategories() throws Exception {
		for (Business business : Globals.BUSINESSES) {
			if (business.getStars() > 0) {
				int checkinsCount = 0;
				int reviewsCount = business.getReview_count();
				double starsCount = business.getStars();

				if (CheckinsMap.containsKey(business.getBusiness_id())) {
					for (int checkinForHour : CheckinsMap.get(business.getBusiness_id()).getCheckin_info().values()) {
						checkinsCount += checkinForHour;
					}
				}

				if (reviewsCount <= 100) {
					Margin0To100ReviewsAvgStars += starsCount;
					Margin0To100ReviewsCount++;
				} else if (reviewsCount <= 200) {
					Margin100To200ReviewsAvgStars += starsCount;
					Margin100To200ReviewsCount++;
				} else if (reviewsCount <= 300) {
					Margin200To300ReviewsAvgStars += starsCount;
					Margin200To300ReviewsCount++;
				} else {
					Margin300PlusReviewsAvgStars += starsCount;
					Margin300PlusReviewsCount++;
				}

				if (checkinsCount <= 500) {
					Margin0To500CheckinsAvgStars += starsCount;
					Margin0To500CheckinsCount++;
				} else if (checkinsCount <= 1000) {
					Margin500To1000CheckinsAvgStars += starsCount;
					Margin500To1000CheckinsCount++;
				} else if (checkinsCount <= 1500) {
					Margin1000To1500CheckinsAvgStars += starsCount;
					Margin1000To1500CheckinsCount++;
				} else {
					Margin1500PlusCheckinsAvgStars += starsCount;
					Margin1500PlusCheckinsCount++;
				}
			}
		}
	}

	private static void printResults() {
		System.out.println("Average stars: Open - " + Formatter.formatDouble(AverageOpenedStars) + "; Closed - "
				+ Formatter.formatDouble(AverageClosedStars) + ";");
		
		System.out.println("Reviews:");
		System.out.println(
					"0-100: " + Formatter.formatDouble(Margin0To100ReviewsAvgStars / Margin0To100ReviewsCount) + "; "
					+ "100-200: " + Formatter.formatDouble(Margin100To200ReviewsAvgStars / Margin100To200ReviewsCount) + "; "
					+ "200-300: " + Formatter.formatDouble(Margin200To300ReviewsAvgStars / Margin200To300ReviewsCount) + "; "
					+ "300+: " + Formatter.formatDouble(Margin300PlusReviewsAvgStars / Margin300PlusReviewsCount) + "; "
				);
		System.out.println();

		System.out.println("Checkins:");
		System.out.println(
					"0-500: " + Formatter.formatDouble(Margin0To500CheckinsAvgStars / Margin0To500CheckinsCount) + "; "
					+ "500-1000: " + Formatter.formatDouble(Margin500To1000CheckinsAvgStars / Margin500To1000CheckinsCount) + "; "
					+ "1000-1500: " + Formatter.formatDouble(Margin1000To1500CheckinsAvgStars / Margin1000To1500CheckinsCount) + "; "
					+ "1500+: " + Formatter.formatDouble(Margin1500PlusCheckinsAvgStars / Margin1500PlusCheckinsCount) + "; "
				);
	}

	public static void calculateDependencies() throws Exception {
		init();
		calculateAveragesForIsOpen();
		calculateCategories();
		calculatePoints();
		printResults();
	}
}

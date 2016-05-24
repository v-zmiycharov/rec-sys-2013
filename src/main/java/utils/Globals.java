package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import data.Business;
import data.Checkin;
import data.Review;
import data.User;
import evaluator.DataModelGenerator;
import evaluator.IdConvertor;

public class Globals {
	public static List<Business> BUSINESSES;
	public static List<Checkin> CHECKINS;
	public static List<User> USERS;

	public static List<Review> TRAIN_REVIEWS;

	public static List<Review> TEST_REVIEWS;

	public static Map<String, Double> UserBasedResults;
	public static Map<String, Double> ItemBasedResults;
	public static Map<String, Double> SvdResults;
	public static Map<String, Double> SvdPlusPlusResults;

	public static Map<String, Double> BusinessAverages;
	public static Map<String, Double> UserAverages;

	public static void init() throws Exception {
		initBusinesses();
		initCheckins();
		initUsers();
		
		boolean trainModels = false;
		if(trainModels)
			initTrainReviews();

		initTestReviews();
		
		IdConvertor.init();
		
		if(trainModels)
			DataModelGenerator.writeDataModel();
	}
	
	private static void initBusinesses() throws Exception {
		BusinessAverages = new HashMap<String, Double>();
		
		Gson gson = new Gson();
		BUSINESSES = new ArrayList<Business>();

		System.out.println("Read Businesses");
		for (String line : FileReader.getLines(Constants.TRAIN_BUSINESS_PATH)) {
			Business business = gson.fromJson(line, Business.class);
			BUSINESSES.add(business);
			BusinessAverages.put(business.getBusiness_id(), business.getStars());
		}
		for (String line : FileReader.getLines(Constants.TEST_BUSINESS_PATH)) {
			BUSINESSES.add(gson.fromJson(line, Business.class));
		}
	}

	private static void initCheckins() throws Exception {
		Gson gson = new Gson();
		CHECKINS = new ArrayList<Checkin>();

		System.out.println("Read Checkins");
		for (String line : FileReader.getLines(Constants.TRAIN_CHECKIN_PATH)) {
			CHECKINS.add(gson.fromJson(line, Checkin.class));
		}
		for (String line : FileReader.getLines(Constants.TEST_CHECKIN_PATH)) {
			CHECKINS.add(gson.fromJson(line, Checkin.class));
		}
	}

	private static void initUsers() throws Exception {
		UserAverages = new HashMap<String, Double>();
		
		Gson gson = new Gson();
		USERS = new ArrayList<User>();

		System.out.println("Read Users");
		for (String line : FileReader.getLines(Constants.TRAIN_USER_PATH)) {
			User user = gson.fromJson(line, User.class);
			USERS.add(user);
			UserAverages.put(user.getUser_id(), user.getAverage_stars());
		}
		for (String line : FileReader.getLines(Constants.TEST_USER_PATH)) {
			USERS.add(gson.fromJson(line, User.class));
		}
	}

	private static void initTrainReviews() throws Exception {
		Gson gson = new Gson();
		TRAIN_REVIEWS = new ArrayList<Review>();

		System.out.println("Read Train reviews");
		for (String line : FileReader.getLines(Constants.TRAIN_REVIEW_PATH)) {
			TRAIN_REVIEWS.add(gson.fromJson(line, Review.class));
		}
	}

	private static void initTestReviews() throws Exception {
		Gson gson = new Gson();
		TEST_REVIEWS = new ArrayList<Review>();

		System.out.println("Read Test reviews");
		for (String line : FileReader.getLines(Constants.TEST_REVIEW_PATH)) {
			TEST_REVIEWS.add(gson.fromJson(line, Review.class));
		}
	}

	public static void readReviews() {
		TRAIN_REVIEWS = new ArrayList<Review>();
		TEST_REVIEWS = new ArrayList<Review>();

		readReviewsBatch(0, 50000);
		readReviewsBatch(50000, 100000);
		readReviewsBatch(100000, 150000);
		readReviewsBatch(150000, 200000);
		readReviewsBatch(200000, 230000);
	}

	private static void readReviewsBatch(int startIndex, int endIndex) {
		System.out.println("Read Train reviews from " + startIndex + " to "
				+ endIndex + ".");
		try {
			InputStream input = new FileInputStream(Constants.TRAIN_REVIEW_PATH);
			JsonReader reader = new JsonReader(new InputStreamReader(input,
					"UTF-8"));
			Gson gson = new Gson();

			reader.beginObject();

			String jsonTag = null;
			int k = startIndex;
			while (reader.hasNext()) {
				jsonTag = reader.nextName();

				reader.beginArray();
				int index = 0;
				while (reader.hasNext()) {

					if (index == endIndex) {
						break;
					}
					if (index >= startIndex) {
						Review review = gson.fromJson(reader, Review.class);
						TRAIN_REVIEWS.add(review);
						k++;
					} else {
						gson.fromJson(reader, Review.class);
					}

					index++;
				}
				if (index == endIndex) {
					break;
				}
				reader.endArray();

				reader.endObject();

			}
			if (k != endIndex) {
				reader.endObject();
			}

			reader.close();
		} catch (Exception ex) {
			System.out.println("ex:" + ex);
		}

	}

	public static void initSubmissionResults() throws Exception {
		UserBasedResults = initSubmissionResults(Constants.SUBMISSION_USER);
		ItemBasedResults = initSubmissionResults(Constants.SUBMISSION_ITEM);
		SvdResults = initSubmissionResults(Constants.SUBMISSION_SVD);
		SvdPlusPlusResults = initSubmissionResults(Constants.SUBMISSION_SVD_PLUS_PLUS);
	}
	
	private static Map<String, Double> initSubmissionResults(String fileName) throws Exception {
		Map<String, Double> result = new HashMap<String, Double>();
		List<String> lines = FileUtils.readLines(new File(fileName));
		lines.remove(0);

		for(String line : lines) {
			String[] splitted = line.split(",");
			result.put(splitted[0], Double.parseDouble(splitted[1]));
		}
		
		return result;
	}
}

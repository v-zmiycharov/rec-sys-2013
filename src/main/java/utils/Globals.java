package utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import data.Business;
import data.Checkin;
import data.Review;
import data.User;

public class Globals {
	public static List<Business> BUSINESSES;
	public static List<Checkin> CHECKINS;
	public static List<User> USERS;

	public static List<Review> TRAIN_REVIEWS;

	public static List<Review> TEST_REVIEWS;

	public static void init() throws Exception {
		Gson gson = new Gson();

		BUSINESSES = new ArrayList<Business>();
		CHECKINS = new ArrayList<Checkin>();
		USERS = new ArrayList<User>();

		TRAIN_REVIEWS = new ArrayList<Review>();
		TEST_REVIEWS = new ArrayList<Review>();

		System.out.println("Read Businesses");
		for (String line : FileReader.getLines(Constants.TRAIN_BUSINESS_PATH)) {
			BUSINESSES.add(gson.fromJson(line, Business.class));
		}
		for (String line : FileReader.getLines(Constants.TEST_BUSINESS_PATH)) {
			BUSINESSES.add(gson.fromJson(line, Business.class));
		}

		System.out.println("Read Checkins");
		for (String line : FileReader.getLines(Constants.TRAIN_CHECKIN_PATH)) {
			CHECKINS.add(gson.fromJson(line, Checkin.class));
		}
		for (String line : FileReader.getLines(Constants.TEST_CHECKIN_PATH)) {
			CHECKINS.add(gson.fromJson(line, Checkin.class));
		}

		System.out.println("Read Users");
		for (String line : FileReader.getLines(Constants.TRAIN_USER_PATH)) {
			USERS.add(gson.fromJson(line, User.class));
		}
		for (String line : FileReader.getLines(Constants.TEST_USER_PATH)) {
			USERS.add(gson.fromJson(line, User.class));
		}

		System.out.println("Read Train reviews");
		for (String line : FileReader.getLines(Constants.TRAIN_REVIEW_PATH)) {
			TRAIN_REVIEWS.add(gson.fromJson(line, Review.class));
		}

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
}

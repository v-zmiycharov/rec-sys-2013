package utils;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import data.*;

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
		for(String line : FileReader.getLines(Constants.TRAIN_BUSINESS_PATH)) {
			BUSINESSES.add(gson.fromJson(line, Business.class));
		}
		for(String line : FileReader.getLines(Constants.TEST_BUSINESS_PATH)) {
			BUSINESSES.add(gson.fromJson(line, Business.class));
		}

		System.out.println("Read Checkins");
		for(String line : FileReader.getLines(Constants.TRAIN_CHECKIN_PATH)) {
			CHECKINS.add(gson.fromJson(line, Checkin.class));
		}
		for(String line : FileReader.getLines(Constants.TEST_CHECKIN_PATH)) {
			CHECKINS.add(gson.fromJson(line, Checkin.class));
		}

		System.out.println("Read Users");
		for(String line : FileReader.getLines(Constants.TRAIN_USER_PATH)) {
			USERS.add(gson.fromJson(line, User.class));
		}
		for(String line : FileReader.getLines(Constants.TEST_USER_PATH)) {
			USERS.add(gson.fromJson(line, User.class));
		}

		System.out.println("Read Train reviews");
		for(String line : FileReader.getLines(Constants.TRAIN_REVIEW_PATH)) {
			TRAIN_REVIEWS.add(gson.fromJson(line, Review.class));
		}

		System.out.println("Read Test reviews");
		for(String line : FileReader.getLines(Constants.TEST_REVIEW_PATH)) {
			TEST_REVIEWS.add(gson.fromJson(line, Review.class));
		}
	}
}

package utils;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import data.*;

public class Globals {
	public static List<Business> TRAIN_BUSINESS;
	public static List<Checkin> TRAIN_CHECKIN;
	public static List<Review> TRAIN_REVIEW;
	public static List<User> TRAIN_USER;

	public static List<Business> TEST_BUSINESS;
	public static List<Checkin> TEST_CHECKIN;
	public static List<Review> TEST_REVIEW;
	public static List<User> TEST_USER;
	
	public static void init() throws Exception {
		Gson gson = new Gson();
		
		TRAIN_BUSINESS = new ArrayList<Business>();
		TRAIN_CHECKIN = new ArrayList<Checkin>();
		TRAIN_REVIEW = new ArrayList<Review>();
		TRAIN_USER = new ArrayList<User>();

		TEST_BUSINESS = new ArrayList<Business>();
		TEST_CHECKIN = new ArrayList<Checkin>();
		TEST_REVIEW = new ArrayList<Review>();
		TEST_USER = new ArrayList<User>();
		
		for(String line : FileReader.getLines(Constants.TRAIN_BUSINESS_PATH)) {
			TRAIN_BUSINESS.add(gson.fromJson(line, Business.class));
		}
		for(String line : FileReader.getLines(Constants.TRAIN_CHECKIN_PATH)) {
			TRAIN_CHECKIN.add(gson.fromJson(line, Checkin.class));
		}
		for(String line : FileReader.getLines(Constants.TRAIN_REVIEW_PATH)) {
			TRAIN_REVIEW.add(gson.fromJson(line, Review.class));
		}
		for(String line : FileReader.getLines(Constants.TRAIN_USER_PATH)) {
			TRAIN_USER.add(gson.fromJson(line, User.class));
		}

		for(String line : FileReader.getLines(Constants.TEST_BUSINESS_PATH)) {
			TEST_BUSINESS.add(gson.fromJson(line, Business.class));
		}
		for(String line : FileReader.getLines(Constants.TEST_CHECKIN_PATH)) {
			TEST_CHECKIN.add(gson.fromJson(line, Checkin.class));
		}
		for(String line : FileReader.getLines(Constants.TEST_REVIEW_PATH)) {
			TEST_REVIEW.add(gson.fromJson(line, Review.class));
		}
		for(String line : FileReader.getLines(Constants.TEST_USER_PATH)) {
			TEST_USER.add(gson.fromJson(line, User.class));
		}
	}
}

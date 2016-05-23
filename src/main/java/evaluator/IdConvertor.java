package evaluator;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;

import data.Business;
import data.Review;
import data.User;
import utils.*;

public class IdConvertor {
	private static Map<String, Double> BusinessesConvertorMap;
	private static Map<String, Double> UsersConvertorMap;

	public static void init() throws Exception {
		Gson gson = new Gson();

		File businessFile = new File(Constants.BUSINESS_CONVERTOR);
		if (businessFile.exists()) {
			BusinessesConvertorMap = gson.fromJson(FileUtils.readFileToString(businessFile),
					new HashMap<String, Double>().getClass());
		} else {
			BusinessesConvertorMap = new HashMap<String, Double>();
			double i = 1;
			for (Business business : Globals.BUSINESSES) {
				BusinessesConvertorMap.put(business.getBusiness_id(), i);
				i++;
			}

			businessFile.createNewFile();
			FileUtils.writeStringToFile(businessFile, gson.toJson(BusinessesConvertorMap));
		}

		File userFile = new File(Constants.USER_CONVERTOR);
		if (userFile.exists()) {
			UsersConvertorMap = gson.fromJson(FileUtils.readFileToString(userFile),
					new HashMap<String, Integer>().getClass());
		} else {
			UsersConvertorMap = new HashMap<String, Double>();
			double i = 1;
			for (User user : Globals.USERS) {
				UsersConvertorMap.put(user.getUser_id(), i);
				i++;
			}

			Set<String> keys = UsersConvertorMap.keySet();

			for (Review review : Globals.TRAIN_REVIEWS) {
				if (!keys.contains(review.getUser_id())) {
					UsersConvertorMap.put(review.getUser_id(), i);
					i++;
				}
			}

			for (Review review : Globals.TEST_REVIEWS) {
				if (!keys.contains(review.getUser_id())) {
					UsersConvertorMap.put(review.getUser_id(), i);
					i++;
				}
			}

			userFile.createNewFile();
			FileUtils.writeStringToFile(userFile, gson.toJson(UsersConvertorMap));
		}
	}

	public static int convertBusinessId(String businessId) throws Exception {
		return BusinessesConvertorMap.get(businessId).intValue();
	}

	public static int convertUserId(String userId) throws Exception {
		return UsersConvertorMap.get(userId).intValue();
	}
}

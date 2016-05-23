package evaluator;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import data.Business;
import data.Review;
import data.User;
import utils.Globals;

public class IdConvertor {
	private static Map<String, Integer> BusinessesConvertorMap;
	private static Map<String, Integer> UsersConvertorMap;

	public static void init() {
		BusinessesConvertorMap = new HashMap<String, Integer>();
		int i = 1;
		for (Business business : Globals.BUSINESSES) {
			BusinessesConvertorMap.put(business.getBusiness_id(), i);
			i++;
		}

		UsersConvertorMap = new HashMap<String, Integer>();
		i = 1;
		for (User user : Globals.USERS) {
			UsersConvertorMap.put(user.getUser_id(), i);
			i++;
		}
		
		Set<String> keys = UsersConvertorMap.keySet();

		for(Review review : Globals.TRAIN_REVIEWS) {
			if(!keys.contains(review.getUser_id())) {
				UsersConvertorMap.put(review.getUser_id(), i);
				i++;
			}
		}
		
		for(Review review : Globals.TEST_REVIEWS) {
			if(!keys.contains(review.getUser_id())) {
				UsersConvertorMap.put(review.getUser_id(), i);
				i++;
			}
		}
	}

	public static int convertBusinessId(String businessId) throws Exception {
		return BusinessesConvertorMap.get(businessId);
	}

	public static int convertUserId(String userId) throws Exception {
		return UsersConvertorMap.get(userId);
	}
}

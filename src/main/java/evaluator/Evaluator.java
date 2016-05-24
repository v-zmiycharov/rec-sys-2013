package evaluator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.mahout.cf.taste.common.NoSuchItemException;
import org.apache.mahout.cf.taste.common.NoSuchUserException;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.svd.ALSWRFactorizer;
import org.apache.mahout.cf.taste.impl.recommender.svd.SVDPlusPlusFactorizer;
import org.apache.mahout.cf.taste.impl.recommender.svd.SVDRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import utils.Constants;
import utils.Globals;
import data.Review;
import statistics.StatisticsClassifier;

public class Evaluator {

	private DataModel model;

	public Evaluator(String modelPath) throws IOException {
		File file = new File(modelPath);
		model = new FileDataModel(file, "::");
	}

	public void userBased() throws Exception {
		System.out.println("Users based started!" + new Date().toString());

		UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
		UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.5, similarity, model);
		UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);

		evaluateReviews(recommender);

		System.out.println("Users based ended!" + new Date().toString());

	}

	public void itemBased() throws Exception {
		System.out.println("Items based started!" + new Date().toString());

		ItemSimilarity itemSimilarity = new EuclideanDistanceSimilarity(model);
		Recommender recommender = new GenericItemBasedRecommender(model, itemSimilarity);

		evaluateReviews(recommender);

		System.out.println("Items based ended!" + new Date().toString());
	}

	public void svd() throws Exception {
		System.out.println("SVD started!" + new Date().toString());

		Recommender recommender = new SVDRecommender(model, new ALSWRFactorizer(model, 30, 0.065, 100));

		evaluateReviews(recommender);

		System.out.println("SVD ended!" + new Date().toString());
	}

	public void svdPlusPlus() throws Exception {
		System.out.println("SVD++ started!" + new Date().toString());

		Recommender recommender = new SVDRecommender(model, new SVDPlusPlusFactorizer(model, 30, 100));

		evaluateReviews(recommender);

		System.out.println("SVD++ ended!" + new Date().toString());
	}

	private void evaluateReviews(Recommender recommender) throws Exception {
		for (Review review : Globals.TEST_REVIEWS) {
			if(Globals.UserAverages.containsKey(review.getUser_id())
					&& Globals.BusinessAverages.containsKey(review.getBusiness_id())) {
				float estimation = recommender.estimatePreference(IdConvertor.convertUserId(review.getUser_id()),
						IdConvertor.convertBusinessId(review.getBusiness_id()));
				if (estimation >= 0) {
					if(estimation > 5) {
						estimation = 5;
					}
					review.setStars(estimation);
					continue;
				}
			}
			else if(Globals.BusinessAverages.containsKey(review.getBusiness_id())) {
				review.setStars(Globals.BusinessAverages.get(review.getBusiness_id()));
				continue;
			}
			else if(Globals.UserAverages.containsKey(review.getUser_id())) {
				review.setStars(Globals.UserAverages.get(review.getUser_id()));
				continue;
			}
			
			double stars = 0.33 * StatisticsClassifier.getStarsByIsOpen(review.getBusiness_id())
					+ 0.33 * StatisticsClassifier.getStarsByCheckinsCount(review.getBusiness_id())
					+ 0.34 * StatisticsClassifier.getStarsByReviewsCount(review.getBusiness_id());

			review.setStars(stars);
		}
	}
	
	public static void calculateFinalResults() throws Exception {
		for (Review review : Globals.TEST_REVIEWS) {
			double stars = 0.04 * StatisticsClassifier.getStarsByIsOpen(review.getBusiness_id())
					+ 0.04 * StatisticsClassifier.getStarsByCheckinsCount(review.getBusiness_id())
					+ 0.04 * StatisticsClassifier.getStarsByReviewsCount(review.getBusiness_id())
					+ 0.88 * Globals.UserBasedResults.get(review.getReview_id());

			review.setStars(stars);
		}
	}
}

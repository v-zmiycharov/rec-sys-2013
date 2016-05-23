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
import data.Review;


public class Evaluator {
	
	private DataModel model;
	
	public Evaluator(String modelPath) throws IOException{
		File file = new File(modelPath);
		model = new FileDataModel(file, "::");
	}

	public void userBased(List<Review> reviewsForEvaluation) throws TasteException{
		System.out.println("Users based started!" +  new Date().toString());
	
		UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
		UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.5, similarity, model);
		UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
		
		for(Review review : reviewsForEvaluation){
			try{
			float estimation = recommender.estimatePreference(IdConvertor.intHash(review.getUser_id()), IdConvertor.intHash(review.getBusiness_id()));
			if(estimation >= 0){
				review.setStars(estimation);
				//System.out.println("Review: " + review.getReview_id() + " = " + review.getUser_id() + " - " + review.getBusiness_id() + ": "+estimation );
			}
				
//			System.out.println("Review: " + review.getReview_id() + " = " + review.getUser_id() + " - " + review.getBusiness_id() + ": "+estimation );
			}catch(NoSuchUserException ex){
//				System.out.println("User " + review.getUser_id() + " not found!");
			}catch(NoSuchItemException ex){
//				System.out.println("Item " + review.getBusiness_id() + " not found!");
			}
		}
		
		System.out.println("Users based ended!" +  new Date().toString());

	}
	
	public  void itemBased(List<Review> reviewsForEvaluation) throws TasteException{
		System.out.println("Items based started!" +  new Date().toString());
		
		ItemSimilarity itemSimilarity = new EuclideanDistanceSimilarity (model);
		Recommender recommender = new GenericItemBasedRecommender(model,itemSimilarity);
		
		for(Review review : reviewsForEvaluation){
			try{
			float estimation = recommender.estimatePreference(IdConvertor.intHash(review.getUser_id()), IdConvertor.intHash(review.getBusiness_id()));
			if(estimation >= 0)
				review.setStars(estimation);
//				System.out.println("Review: " + review.getReview_id() + " = " + review.getUser_id() + " - " + review.getBusiness_id() + ": "+estimation );
			}catch(NoSuchUserException ex){
//				System.out.println("User " + review.getUser_id() + " not found!");
			}catch(NoSuchItemException ex){
//				System.out.println("Item " + review.getBusiness_id() + " not found!");
			}
		}

		System.out.println("Items based ended!" +  new Date().toString());
	}
	
	public  void svd(List<Review> reviewsForEvaluation) throws TasteException{
		System.out.println("SVD started!" +  new Date().toString());
		
		Recommender recommender=new SVDRecommender(model,new ALSWRFactorizer(model, 4, 0.065, 100));
		
		for(Review review : reviewsForEvaluation){
			try{
			float estimation = recommender.estimatePreference(IdConvertor.intHash(review.getUser_id()), IdConvertor.intHash(review.getBusiness_id()));
			if(estimation >= 0)
				review.setStars(estimation);
//				System.out.println("Review: " + review.getReview_id() + " = " + review.getUser_id() + " - " + review.getBusiness_id() + ": "+estimation );
			}catch(NoSuchUserException ex){
//				System.out.println("User " + review.getUser_id() + " not found!");
			}catch(NoSuchItemException ex){
//				System.out.println("Item " + review.getBusiness_id() + " not found!");
			}
		}
		
		System.out.println("SVD ended!" +  new Date().toString());
	}
	
	public  void svdPlusPlus(List<Review> reviewsForEvaluation) throws TasteException{
		System.out.println("SVD++ started!" +  new Date().toString());
		
		Recommender recommender=new SVDRecommender(model,new SVDPlusPlusFactorizer(model, 4, 50));
		
		for(Review review : reviewsForEvaluation){
			try{
			float estimation = recommender.estimatePreference(IdConvertor.intHash(review.getUser_id()), IdConvertor.intHash(review.getBusiness_id()));
			if(estimation >= 0)
				review.setStars(estimation);
//				System.out.println("Review: " + review.getReview_id() + " = " + review.getUser_id() + " - " + review.getBusiness_id() + ": "+estimation );
			}catch(NoSuchUserException ex){
//				System.out.println("User " + review.getUser_id() + " not found!");
			}catch(NoSuchItemException ex){
//				System.out.println("Item " + review.getBusiness_id() + " not found!");
			}
		}

		System.out.println("SVD++ ended!" +  new Date().toString());
	}

}

package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import data.Review;

public class SubmissionGenerator {
	public static void generateSubmissions() throws Exception {
		System.out.println("Generate submission");
		
		File outputFile = new File(Constants.SUBMISSION_PATH);
		
		if(!outputFile.exists()) {
			outputFile.createNewFile();
		}
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile, false));
		
		bw.append("review_id,stars");
		
		for(Review review : Globals.TEST_REVIEWS) {
			bw.newLine();
			bw.append(review.getReview_id() + "," + review.getStars());
		}
		
		bw.close();
	}
}

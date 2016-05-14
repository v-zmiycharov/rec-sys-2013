import data.Review;
import utils.Globals;
import utils.SubmissionGenerator;

public class Program {

	public static void main(String[] args) throws Exception {
		Globals.init();
		
		// FAKE !!! TODO: Remove
		for(Review review : Globals.TEST_REVIEWS) {
			review.setStars(2.5);
		}
		
		SubmissionGenerator.generateSubmissions();

		System.out.println("Finished!");
	}

}

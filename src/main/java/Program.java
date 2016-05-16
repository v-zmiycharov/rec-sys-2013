import data.Review;
import statistics.Dependencies;
import utils.Globals;
import utils.SubmissionGenerator;

public class Program {

	public static void main(String[] args) throws Exception {
		Globals.init();
		
		Dependencies.calculateDependencies();
		
		// FAKE !!! TODO: Remove
		for(Review review : Globals.TEST_REVIEWS) {
			review.setStars(2.5);
		}
		
		SubmissionGenerator.generateSubmissions();

		System.out.println("Finished!");
	}

}

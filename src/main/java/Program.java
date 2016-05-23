import statistics.Dependencies;
import utils.Globals;
import utils.SubmissionGenerator;
import data.Review;

public class Program {

	public static void main(String[] args) throws Exception {
		Globals.init();
		
		Dependencies.calculateDependencies();
		
//		Globals.readReviews();
//		DataModelGenerator.writeDataModel(Constants.REVIEWS_DAT_FILE);
		
		//Uncomment MEEEEE
//		Evaluator eval = new Evaluator(Constants.REVIEWS_DAT_FILE);


		//Uncomment ONE OF MEEEEE
//		eval.userBased( Globals.TEST_REVIEWS);
//		eval.itemBased(Globals.TEST_REVIEWS);
//		eval.svd(Globals.TEST_REVIEWS);
//		eval.svdPlusPlus(Globals.TEST_REVIEWS);
		//Uncomment ONE OF MEEEEE
	
		
		// FAKE !!! TODO: Remove
		for(Review review : Globals.TEST_REVIEWS) {
			review.setStars(2.5);
		}
		
		SubmissionGenerator.generateSubmissions();
		

		System.out.println("Finished!");
	}

}

import statistics.Dependencies;
import utils.Constants;
import utils.Globals;
import utils.SubmissionGenerator;
import data.Review;
import evaluator.DataModelGenerator;
import evaluator.Evaluator;

public class Program {

	public static void main(String[] args) throws Exception {
		Globals.init();
		
		boolean runEval = true;
		
		if(runEval) {
			Evaluator eval = new Evaluator(Constants.REVIEWS_DAT_FILE);
	
			// Uncomment ONE
			// eval.userBased(Globals.TEST_REVIEWS);
			eval.itemBased(Globals.TEST_REVIEWS);
			// eval.svd(Globals.TEST_REVIEWS);
 			// eval.svdPlusPlus(Globals.TEST_REVIEWS);
		}
		else {
			// read from submission files
			Globals.initSubmissionResults();
			
			Evaluator.calculateFinalResults(Globals.TEST_REVIEWS);
		}
		
		SubmissionGenerator.generateSubmissions();
		
		System.out.println("Finished!");
	}

}

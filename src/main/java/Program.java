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
		
		boolean runEval = false;
		
		if(runEval) {
			Evaluator eval = new Evaluator(Constants.REVIEWS_DAT_FILE);
	
			// Uncomment ONE
			// eval.userBased();
			// eval.itemBased();
			eval.svd();
			// eval.svdPlusPlus();
		}
		else {
			// read from submission files
			Globals.initSubmissionResults();
			
			Evaluator.calculateFinalResults();
		}
		
		SubmissionGenerator.generateSubmissions();
		
		System.out.println("Finished!");
	}

}

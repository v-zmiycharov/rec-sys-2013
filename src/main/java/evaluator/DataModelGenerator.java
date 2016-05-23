package evaluator;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import org.apache.commons.io.FileUtils;

import utils.Constants;
import utils.Globals;
import data.Review;

public class DataModelGenerator {

private static Writer writer;
	
	public static void writeDataModel() throws Exception {
		if (Globals.TRAIN_REVIEWS != null) {
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(Constants.REVIEWS_DAT_FILE, true), "utf-8"));
			
			for (Review review : Globals.TRAIN_REVIEWS) {
				
				String line = IdConvertor.convertUserId(review.getUser_id()) + "::" + 
								IdConvertor.convertBusinessId(review.getBusiness_id()) + "::" + 
								review.getStars() + "::" + 
								review.getDate().getTime()/1000;
				
				line = line.replace("\n", " ");
				try {
					writer.append(line + "\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

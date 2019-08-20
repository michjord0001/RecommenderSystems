package alg.ib;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import alg.ib.neighbourhood.Neighbourhood;
import alg.ib.neighbourhood.ThresholdNeighbourhood;
import alg.ib.predictor.Predictor;
import alg.ib.predictor.DeviationFromItemMeanPredictor;
import similarity.metric.CosineMetric;
import similarity.metric.SimilarityMetric;
import util.evaluator.Evaluator;
import util.reader.DatasetReader;

public class ExecuteIB_Expt_2 {
	public static void main(String[] args) throws IOException
	{
		// configure the item-based CF algorithm - set the predictor and similarity metric ...
		Predictor predictor = new DeviationFromItemMeanPredictor();
		SimilarityMetric metric = new CosineMetric();
		
		// set the paths and filenames of the item file, genome scores file, train file and test file ...
		String folder = "ml-20m-2018-2019";
		String itemFile = folder + File.separator + "movies-sample.txt";
		String itemGenomeScoresFile = folder + File.separator + "genome-scores-sample.txt";
		String trainFile = folder + File.separator + "train.txt";
		String testFile = folder + File.separator + "test.txt";	
		
		// set the path and filename of the output file ...
		String outputFile = "results" + File.separator + "Expt2-Predictions.txt";
		
		//Clear our RMSE/Coverage output file
//		BufferedWriter pw_reset = new BufferedWriter(new FileWriter("results"+ File.separator + "Expt1-RMSE_Coverage.txt")); //Output format: Algorithm, Neighbourhood size, RMSE, Coverage 
//		pw_reset.write("");
//		pw_reset.close();
		
		for (double t = 0.00; t<0.75; t+=0.05) //Iterate through t values: 0, 0.05, 0.1,..., 0.7 - not sure why 0.7 is not included?
		{
			Neighbourhood neighbourhood = new ThresholdNeighbourhood(t);
			////////////////////////////////////////////////
			// Evaluates the CF algorithm (do not change!!):
			// - the RMSE (if actual ratings are available) and coverage are output to screen
			// - output file is created
			DatasetReader reader = new DatasetReader(itemFile, itemGenomeScoresFile, trainFile, testFile);
			ItemBasedCF ibcf = new ItemBasedCF(predictor, neighbourhood, metric, reader);
			Evaluator eval = new Evaluator(ibcf, reader.getTestData());
			
			// Write to output file
			eval.writeResults(outputFile);
			
			// Get RMSE and coverage
			Double RMSE = eval.getRMSE();
			double coverage = eval.getCoverage();
			
			//Write Threshold, RMSE and Coverage to file
			BufferedWriter pw = new BufferedWriter(new FileWriter("results"+ File.separator + "Expt2-RMSE_Coverage.txt", true)); //Output format:Predictor, Threshold size, RMSE, Coverage 
			if(RMSE != null) pw.write(predictor.toString() + ", " + Double.toString(t) + ", " + Double.toString(RMSE) + ", " + Double.toString(coverage));
			pw.newLine();
			pw.close();	
			
//			System.out.printf("Threshold Size: %.2f\n", t);
//			System.out.printf("RMSE: %.2f\n", RMSE);
//			System.out.printf("Coverage: %.2f\n", coverage);
//			System.out.printf("Neighbourhood Size: %d\n\n", neighbourhood.get_size());
		}
	}
}

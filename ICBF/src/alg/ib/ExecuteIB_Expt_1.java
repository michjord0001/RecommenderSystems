package alg.ib;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import alg.ib.neighbourhood.NearestNeighbourhood;
import alg.ib.neighbourhood.Neighbourhood;
import alg.ib.predictor.Predictor;
import alg.ib.predictor.SimpleAveragePredictor;
import alg.ib.predictor.DeviationFromItemMeanPredictor;
import alg.ib.predictor.WeightedAveragePredictor;
import alg.ib.predictor.NonPersonalisedPredictor;
import similarity.metric.CosineMetric;
import similarity.metric.SimilarityMetric;
import util.evaluator.Evaluator;
import util.reader.DatasetReader;


public class ExecuteIB_Expt_1 {
	public static void main(String[] args) throws IOException
	{
		// configure the item-based CF algorithm - set the predictor and similarity metric ...
		Predictor[] predictors = new Predictor[4];	//Iterable object array of predictors
		predictors[0] = new NonPersonalisedPredictor(); 
		predictors[1] = new SimpleAveragePredictor();
		predictors[2] = new WeightedAveragePredictor();
		predictors[3] = new DeviationFromItemMeanPredictor();
		
		SimilarityMetric metric = new CosineMetric();
		
		// set the paths and filenames of the item file, genome scores file, train file and test file ...
		String folder = "ml-20m-2018-2019";
		String itemFile = folder + File.separator + "movies-sample.txt";
		String itemGenomeScoresFile = folder + File.separator + "genome-scores-sample.txt";
		String trainFile = folder + File.separator + "train.txt";
		String testFile = folder + File.separator + "test.txt";	
		
		// set the path and filename of the output file ...
		String outputFile = "results" + File.separator + "Expt1-Predictions.txt";
		
		//Clear our RMSE/Coverage output file
//		BufferedWriter pw_reset = new BufferedWriter(new FileWriter("results"+ File.separator + "Expt1-RMSE_Coverage.txt")); //Output format: Algorithm, Neighbourhood size, RMSE, Coverage 
//		pw_reset.write("");
//		pw_reset.close();
		
		for(Predictor predictor : predictors)	//Iterate through predictors: non- personalised, simple average, weighted average, and deviation from item-mean.
		{
			for (int k = 10; k<=250; k+=10) //Iterate through k values: 10, 20, 30,..., 250
			{
				Neighbourhood neighbourhood = new NearestNeighbourhood(k); //Configure nearest neighbourhood with k nearest neighbours

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
				Double coverage = eval.getCoverage();
				
				//Write Neighbourhood Size, RMSE and Coverage to file
				BufferedWriter pw = new BufferedWriter(new FileWriter("results"+ File.separator + "Expt1-RMSE_Coverage.txt", true)); //Output format: Algorithm, Neighbourhood size, RMSE, Coverage 
				if(RMSE != null) pw.write(predictor.toString() + ", " + Integer.toString(k) + ", " + Double.toString(RMSE) + ", " + Double.toString(coverage));
				pw.newLine();
				pw.close();
			}
		}
	}
}

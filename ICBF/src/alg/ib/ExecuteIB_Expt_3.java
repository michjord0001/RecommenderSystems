package alg.ib;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import alg.ib.neighbourhood.NearestNeighbourhood;
import alg.ib.neighbourhood.Neighbourhood;
import alg.ib.predictor.Predictor;
import alg.ib.predictor.DeviationFromItemMeanPredictor;
import similarity.metric.CosineMetric;
import similarity.metric.MeanSquaredDifferenceMetric;
import similarity.metric.PearsonMetric;
import similarity.metric.PearsonSigWeightingMetric;
import similarity.metric.SimilarityMetric;
import util.evaluator.Evaluator;
import util.reader.DatasetReader;

public class ExecuteIB_Expt_3 {
	public static void main(String[] args) throws IOException
	{
		// configure the item-based CF algorithm - set the predictor and similarity metric ...
		int n = 200; //Neighbourhood size
		Predictor predictor = new DeviationFromItemMeanPredictor();
		Neighbourhood neighbourhood = new NearestNeighbourhood(n); 
		SimilarityMetric[] metrics = new SimilarityMetric[4];
		metrics[0] = new CosineMetric();
		metrics[1] = new PearsonMetric();
		metrics[2] = new PearsonSigWeightingMetric(50);
		metrics[3] = new MeanSquaredDifferenceMetric();
		
		// set the paths and filenames of the item file, genome scores file, train file and test file ...
		String folder = "ml-20m-2018-2019";
		String itemFile = folder + File.separator + "movies-sample.txt";
		String itemGenomeScoresFile = folder + File.separator + "genome-scores-sample.txt";
		String trainFile = folder + File.separator + "train.txt";
		String testFile = folder + File.separator + "test.txt";	
		
		// set the path and filename of the output file ...
		String outputFile = "results" + File.separator + "Expt3-Predictions.txt";
		
		//Clear our RMSE/Coverage output file
//		BufferedWriter pw_reset = new BufferedWriter(new FileWriter("results"+ File.separator + "Expt1-RMSE_Coverage.txt")); //Output format: Algorithm, Neighbourhood size, RMSE, Coverage 
//		pw_reset.write("");
//		pw_reset.close();
		
		for(SimilarityMetric metric : metrics)	//Iterate through predictors: non- personalised, simple average, weighted average, and deviation from item-mean.
		{
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
			
			//Write RMSE and Coverage to file
			BufferedWriter pw = new BufferedWriter(new FileWriter("results"+ File.separator + "Expt3-RMSE_Coverage.txt", true)); //Output format: Metric, Neighbourhood Size, RMSE, Coverage
			if(RMSE != null) pw.write(metric.toString() + ", " + Integer.toString(n) + ", " + Double.toString(RMSE) + ", " + Double.toString(coverage));
			pw.newLine();
			pw.close();
		}
	}
}

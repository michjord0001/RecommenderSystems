package similarity.metric;

import java.util.Set;

import profile.Profile;

public class MeanSquaredDifferenceMetric implements SimilarityMetric {
	/**
	 * constructor - creates a new MeanSquaredDifferenceMetric object
	 */
	public MeanSquaredDifferenceMetric()
	{
	}
	
	/**
	 * computes the similarity between profiles
	 * @param profile 1 
	 * @param profile 2
	 */
	public double getSimilarity(final Profile p1, final Profile p2)
	{
		double MSDNumerator = 0.0; 
		double rMax = 5.0, rMin = 0.5; //Given in question
		Set<Integer> common = p1.getCommonIds(p2);
		
		if (common.size() == 0){
			return 0;
		}
		
		for (Integer id: common){
			MSDNumerator += Math.pow(p1.getValue(id) - p2.getValue(id), 2); //Compute summation from MSD Formula
		}
		
		double MSD = MSDNumerator / common.size();
		return 1 - (MSD / Math.pow(rMax - rMin, 2));
	}
}

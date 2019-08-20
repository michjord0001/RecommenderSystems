package similarity.metric;

import java.util.Set;

import profile.Profile;

public class CosineMetric implements SimilarityMetric{
	/**
	 * constructor - creates a new CosineMetric object
	 */
	public CosineMetric()
	{
	}
	
	/**
	 * computes the similarity between profiles
	 * @param profile 1 
	 * @param profile 2
	 */
	public double getSimilarity(final Profile p1, final Profile p2)
	{
		double numerator = 0.0, denominator1 = 0.0, denominator2 = 0.0;
		Set<Integer> common = p1.getCommonIds(p2);
		
		for (Integer id: common){
			numerator += (p1.getValue(id) + p2.getValue(id));
			denominator1 += Math.pow(p1.getValue(id), 2);
			denominator2 += Math.pow(p2.getValue(id), 2);
		}
		
		double denominator = Math.sqrt(denominator1) * Math.sqrt(denominator2);
		return (denominator == 0) ? 0 : (numerator / denominator); //Accounting for zero division
	}
}

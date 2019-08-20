package alg.ib.predictor;

import java.util.Map;

import alg.ib.neighbourhood.Neighbourhood;
import profile.Profile;
import similarity.SimilarityMap;

public class WeightedAveragePredictor implements Predictor {
	
	/**
	 * constructor - creates a new ThresholdNeighbourhood object
	 * @param t - the threshold value for the neighbourhood
	 */
	public WeightedAveragePredictor()
	{
	}
	
	/**
	 * @returns the target user's predicted rating for the target item or null if a prediction cannot be computed
	 * @param userId - the numeric ID of the target user
	 * @param itemId - the numerid ID of the target item
	 * @param userProfileMap - a map containing user profiles
	 * @param itemProfileMap - a map containing item profiles
	 * @param neighbourhood - a Neighbourhood object
	 * @param simMap - a SimilarityMap object containing item-item similarities
	 */
	
	@Override
	public Double getPrediction(Integer userId, Integer itemId, 
			Map<Integer, Profile> userProfileMap, Map<Integer, Profile> itemProfileMap, 
			Neighbourhood neighbourhood, SimilarityMap simMap) 
	{
		double numerator = 0, denominator = 0;
		
		for(Integer id: userProfileMap.get(userId).getIds()) //Iterate over target userId's
		{
			if(neighbourhood.isNeighbour(itemId, id)) //Ensuring current item is in the neighbourhood.
			{
				numerator += simMap.getSimilarity(userId, id) * userProfileMap.get(userId).getValue(id);
				denominator += Math.abs(simMap.getSimilarity(userId, id)) + .000000001; //Testing, ensuring denomoinator != 0
			}
		}

		return (denominator == 0)? null : numerator/denominator; //Return prediction score
	}
}

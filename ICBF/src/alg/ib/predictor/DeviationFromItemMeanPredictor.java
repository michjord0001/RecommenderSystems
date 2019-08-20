package alg.ib.predictor;

import java.util.Map;

import alg.ib.neighbourhood.Neighbourhood;
import profile.Profile;
import similarity.SimilarityMap;

public class DeviationFromItemMeanPredictor implements Predictor {
	public DeviationFromItemMeanPredictor()
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
	public Double getPrediction(Integer userId, Integer itemId, Map<Integer, Profile> userProfileMap,
			Map<Integer, Profile> itemProfileMap, Neighbourhood neighbourhood, SimilarityMap simMap) {
		double numerator = 0, denominator = 0;

		for (Integer id: itemProfileMap.get(itemId).getIds()) //Iterate over target user id's
		{
			if(neighbourhood.isNeighbour(id, itemId)) //Ensuring current item is in the neighbourhood.
			{
				double ratingDifference = Math.abs(itemProfileMap.get(itemId).getValue(id) - itemProfileMap.get(itemId).getMeanValue()); //Used numerator
				numerator += simMap.getSimilarity(itemId, id) * ratingDifference;
				denominator += Math.abs(simMap.getSimilarity(itemId, id)) + 0.000000001;
			}
		}

		return (denominator == 0) ? null : numerator/denominator;
	}
}

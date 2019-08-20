/**
 * An interface to compute the target user's predicted rating for the target item (item-based CF)
 * 
 * Michael O'Mahony
 * 20/01/2011
 */

package alg.ib.predictor;

import java.util.Map;

import alg.ib.neighbourhood.Neighbourhood;
import similarity.SimilarityMap;
import profile.Profile;

public interface Predictor 
{
	/**
	 * @returns the target user's predicted rating for the target item or null if a prediction cannot be computed
	 * @param userId - the numeric ID of the target user
	 * @param itemId - the numerid ID of the target item
	 * @param userProfileMap - a map containing user profiles
	 * @param itemProfileMap - a map containing item profiles
	 * @param neighbourhood - a Neighbourhood object
	 * @param simMap - a SimilarityMap object containing item-item similarities
	 */
	public Double getPrediction(final Integer userId, final Integer itemId, final Map<Integer,Profile> userProfileMap, final Map<Integer,Profile> itemProfileMap, final Neighbourhood neighbourhood, final SimilarityMap simMap);
}

/**
// * Compute the similarity between two items based on the Cosine between item ratings
 */ 

package alg.np.similarity.metric;

import util.reader.DatasetReader;

//Self imports
import profile.Profile;
import java.util.Set;

public class RatingMetric implements SimilarityMetric
{
	private DatasetReader reader; // dataset reader

	/**
	 * constructor - creates a new RatingMetric object
	 * @param reader - dataset reader
	 */
	public RatingMetric(final DatasetReader reader)
	{
		this.reader = reader;
	}

	/**
	 * computes the similarity between items
	 * @param X - the id of the first item 
	 * @param Y - the id of the second item
	 */
	public double getItemSimilarity(final Integer X, final Integer Y)
	{
		Profile movieX = reader.getItemProfiles().get(X);	//Load rating profile for movie X
		Profile movieY = reader.getItemProfiles().get(Y);		
		Set<Integer> commonUserIds = movieX.getCommonIds(movieY);	//Set of all userId's that rated both movie X and Y. Removes need to 
		double dotProduct = 0.0;

		for (int userId: commonUserIds){	//Loop through only common users.
			double XRating = movieX.getValue(userId);
			double YRating = movieY.getValue(userId);
//			double XRating = (profiles.get(userId).getValue(X) == null) ? 0.0 : profiles.get(userId).getValue(X);	//How I could have accounted for null values.
			dotProduct += XRating * YRating;	//Dot product summation in Cosine Similarity Formula
		}
		double denominator = movieX.getNorm() * movieY.getNorm();	//Using framework from Profile.
		double cosineSimilarity = (denominator == 0) ? 0 : dotProduct / denominator;		
		return cosineSimilarity;
	}
}

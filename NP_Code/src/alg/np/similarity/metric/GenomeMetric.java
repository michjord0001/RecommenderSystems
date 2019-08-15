/**
 * Compute the similarity between two items based on the Cosine between item genome scores
 */ 

package alg.np.similarity.metric;

import util.reader.DatasetReader;


//Self imports here
import java.util.Set;
import profile.Profile;


public class GenomeMetric implements SimilarityMetric
{
	private DatasetReader reader; // dataset reader
	
	/**
	 * constructor - creates a new GenomeMetric object
	 * @param reader - dataset reader
	 */
	public GenomeMetric(final DatasetReader reader)
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
		Profile movieX = reader.getItem(X).getGenomeScores(); 		//Load genome score profile for movie X
		Profile movieY = reader.getItem(Y).getGenomeScores();
		Set<Integer> commonUserIds = movieX.getCommonIds(movieY);
		double dotProduct = 0.0;
		
		for (int userId: commonUserIds){	//Loop through tagId's. Note - tagId's same for both X and Y.
			dotProduct += movieX.getValue(userId) * movieY.getValue(userId);	//Dot product for Cosine Similarity formula.
		}
		
		double denominator = movieX.getNorm() * movieY.getNorm();	//Using framework from Profile.

		//Division by zero exception
		double cosineSimilarity = (denominator == 0) ? 0 : dotProduct / denominator;
		return cosineSimilarity;
	}
}

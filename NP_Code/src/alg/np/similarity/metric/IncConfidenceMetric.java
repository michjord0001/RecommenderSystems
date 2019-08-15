/**
 * Compute the similarity between two items based on increase in confidence
 */ 

package alg.np.similarity.metric;

import profile.Profile;
import util.reader.DatasetReader;

//Self imports
import java.util.Set;


public class IncConfidenceMetric implements SimilarityMetric
{
	private static double RATING_THRESHOLD = 4.0; // the threshold rating for liked items 
	private DatasetReader reader; // dataset reader
	
	/**
	 * constructor - creates a new IncConfidenceMetric object
	 * @param reader - dataset reader
	 */
	public IncConfidenceMetric(final DatasetReader reader)
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
		Profile movieX = reader.getItemProfiles().get(X);
		Profile movieY = reader.getItemProfiles().get(Y);
		double suppXCount = 0.0, suppXYCount = 0.0, supp_XCount = 0.0, supp_XYCount = 0.0;	//Needs to be double for division later.
		Set<Integer> commonUserIds = movieX.getCommonIds(movieY);	//Using framework from Profile. Users that rated both movies X and Y.
		
		if (commonUserIds.size() == 0){	//0 similarity if no common user ratings.
			return 0;
		}

		for (int userId: commonUserIds){
			//Get ratings (null values assigned zero).
			double XRating = movieX.getValue(userId);	//Get movie X rating for commonUserID. No null values as only comparing common users.
			double YRating = movieY.getValue(userId);
			
			//Counting cases for each support.
			suppXCount = (XRating >= RATING_THRESHOLD) ? suppXCount+=1 : suppXCount;
			suppXYCount = (XRating >= RATING_THRESHOLD && YRating >= RATING_THRESHOLD) ? suppXYCount+=1 : suppXYCount;
			supp_XCount = (XRating < RATING_THRESHOLD) ? supp_XCount+=1 : supp_XCount;
			supp_XYCount = (XRating < RATING_THRESHOLD && YRating >= RATING_THRESHOLD) ? supp_XYCount+=1 : supp_XYCount;
		}
		
		//Calculating support values. 
		double suppX = suppXCount / commonUserIds.size();
		double suppXY = suppXYCount / commonUserIds.size();
		double supp_X = supp_XCount / commonUserIds.size();
		double supp_XY = supp_XYCount / commonUserIds.size();
		
		//Division by zero exceptions
		double confXY = (suppX == 0) ? 0 : suppXY / suppX;
		double conf_XY = (supp_X == 0) ? 0 : supp_XY / supp_X;
		double similarity = (conf_XY == 0) ? 0 : confXY / conf_XY;
		return similarity;
	}
}

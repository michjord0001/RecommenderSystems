package alg.ib.neighbourhood;


import profile.Profile;
import similarity.SimilarityMap;

public class ThresholdNeighbourhood extends Neighbourhood{
	private final double t; // the number of neighbours in the neighbourhood
	
	/**
	 * constructor - creates a new ThresholdNeighbourhood object
	 * @param t - the threshold value for the neighbourhood
	 */
	public ThresholdNeighbourhood(final double t)
	{
		super();
		this.t = t;
	}

	public void computeNeighbourhoods(SimilarityMap simMap) {
		for (Integer itemId: simMap.getIds())
		{
			Profile profile = simMap.getSimilarities(itemId); // get the item similarity profile
			if(profile != null)
			{
				for(Integer id: profile.getIds()) // iterate over each item in the profile
				{
					double sim = profile.getValue(id);
					if(sim > t)
						this.add(itemId, id);	//If sim above threshold, return both id's 
				}
			}
		}
	}
}

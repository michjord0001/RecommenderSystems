/**
 * A class that implements the "nearest neighbourhood" technique (item-based CF)
 * 
 * Michael O'Mahony
 * 20/01/2011
 */

package alg.ib.neighbourhood;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import similarity.SimilarityMap;
import profile.Profile;
import util.ScoredThingDsc;

public class NearestNeighbourhood extends Neighbourhood
{
	private final int k; // the number of neighbours in the neighbourhood
	
	/**
	 * constructor - creates a new NearestNeighbourhood object
	 * @param k - the number of neighbours in the neighbourhood
	 */
	public NearestNeighbourhood(final int k)
	{
		super();
		this.k = k;
	}

	/**
	 * stores the neighbourhoods for all items in member Neighbour.neighbourhoodMap
	 * @param simMap - a map containing item-item similarities
	 */
	public void computeNeighbourhoods(final SimilarityMap simMap)
	{
		for(Integer itemId: simMap.getIds()) // iterate over each item
		{
			SortedSet<ScoredThingDsc> ss = new TreeSet<ScoredThingDsc>(); // for the current item, store all similarities in order of descending similarity in a sorted set

			Profile profile = simMap.getSimilarities(itemId); // get the item similarity profile
			if(profile != null)
			{
				for(Integer id: profile.getIds()) // iterate over each item in the profile
				{
					double sim = profile.getValue(id);
					if(sim > 0)
						ss.add(new ScoredThingDsc(sim, id));
				}
			}

			// get the k most similar items (neighbours)
			int counter = 0;
			for(Iterator<ScoredThingDsc> iter = ss.iterator(); iter.hasNext() && counter < k; )
			{
				ScoredThingDsc st = iter.next();
				Integer id = (Integer)st.thing;
				this.add(itemId, id);
				counter++;
			}
		}
	}
}

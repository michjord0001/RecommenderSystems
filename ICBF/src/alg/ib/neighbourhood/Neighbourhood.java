/**
 * An abstract class to compute the neighbourhoods for all items (item-based CF)
 * 
 * Michael O'Mahony
 * 20/01/2011
 */

package alg.ib.neighbourhood;

import similarity.SimilarityMap;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

public abstract class Neighbourhood 
{
	private Map<Integer,Set<Integer>> neighbourhoodMap; // stores the neighbourhood items for each item in a set
	
	/**
	 * constructor - creates a new Neighbourhood object
	 */
	public Neighbourhood()
	{
		neighbourhoodMap = new HashMap<Integer,Set<Integer>>(); // instantiate Neighbour.neighbourhoodMap
	}
	
	/**
	 * @returns true if id2 is a neighbour of id1
	 * @param id1 - the numeric ID of the first item
	 * @param id2 - the numeric ID of the second item
	 */
	public boolean isNeighbour(final Integer id1, final Integer id2)
	{
		if(neighbourhoodMap.containsKey(id1))
		{
			return neighbourhoodMap.get(id1).contains(id2);
		}
		else
			return false;
	}
	
	/**
	 * @returns adds id2 as a neighbour of id1
	 * @param id1 - the numeric ID of the first item
	 * @param id2 - the numeric ID of the second item
	 */
	public void add(final Integer id1, final Integer id2)
	{
		Set<Integer> set = neighbourhoodMap.containsKey(id1) ? neighbourhoodMap.get(id1) : new HashSet<Integer>();
		set.add(id2);
		neighbourhoodMap.put(id1, set);
	}
	
	/**
	 * stores the neighbourhoods for all items in member neighbourhoodMap - must be called before isNeighbour(Integer,Integer).
	 * @param simMap - a map containing item-item similarities
	 */
	public abstract void computeNeighbourhoods(final SimilarityMap simMap);
	
//	public int get_size(){
//		return neighbourhoodMap.size();
//	}
}

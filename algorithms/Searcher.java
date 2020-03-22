package algorithms;

import java.util.List;

public interface Searcher<T> //Interface for the Search algorithms
{
	// The search method
	public List<State<T>> search(Searchable<T> s);
	
	// Get how many nodes/states were evaluated by the algorithm (for the priority queue)
	public int getNumberOfStatesEvaluated();
	
}

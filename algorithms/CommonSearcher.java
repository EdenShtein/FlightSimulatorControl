package algorithms;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public abstract class CommonSearcher<T> implements Searcher<T> {
	protected PriorityQueue<State<T>> openList; // the costs of openList are calculated from the initial state
	private int evaluatedStates;
	
	public CommonSearcher() {
		openList = new PriorityQueue<>(1, (s1, s2) -> {   //Predicate for the priority queue (By the cost) 
			return Double.compare(s1.getCost(), s2.getCost());
		});
		
		evaluatedStates = 0;
	}
	
	public void clearOpenList() {
		openList.clear();
	}
	
	public State<T> popOpenList() {
		State<T> tmp = openList.poll();
		if(tmp != null)
			evaluatedStates++;
		
		return tmp;
	}
	// Whoever extends CommonSearcher needs to implement this method
	public abstract List<State<T>> search(Searchable<T> s);
	
	@Override
	public int getNumberOfStatesEvaluated()
	{
		return evaluatedStates;
	}
	
	public boolean addToOpenList(State<T> s)
	{
		// Creates new State so we won't change the real costs
				State<T> tmpState = new State<T>(s);
				tmpState.setCost(
						tmpState.getCost() +
						// Check whether the cameFrom isn't null, if it does, we'll consider the path costs as zero 
						(tmpState.getCameFrom() != null ? tmpState.getCameFrom().getCost() : 0)
				);
				return openList.add(tmpState);
	}
	public boolean isOpenListEmpty()
	{
		return openList.isEmpty();
	}
	
	public boolean isContainsInOpenList(State<T> s)
	{
		return openList.contains(s);
	}
	protected boolean removeFromOpenList(State<T> s)
	{
		return openList.remove(s);
	}
	protected List<State<T>> backTrace(State<T> goalState) //recursive back trace method
	{
		LinkedList<State<T>> pathList = new LinkedList<>();
		
		backTraceHelp(pathList, goalState);
		clearOpenList(); // Clearing the openList so if we'll run the algorithm few times it won't change it.
		return pathList;
	}
	
	private void backTraceHelp(List<State<T>> pathList, State<T> goalState) //Help function for the back trace method
	{
		if(goalState == null)
		{
			return;
		}
		
		pathList.add(goalState);
		backTraceHelp(pathList, goalState.getCameFrom());
	}
	protected State<T> findInOpenList(State<T> s)
	{
		Iterator<State<T>> it = openList.iterator();
		State<T> currentState = null;
		
		while(it.hasNext())
		{
			currentState = it.next();
			if(currentState.equals(s))
				return currentState;
		}
		
		return null;
	}
	

}

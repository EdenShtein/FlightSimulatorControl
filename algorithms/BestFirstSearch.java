package algorithms;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

public class BestFirstSearch<T> extends CommonSearcher<T> { //BFS algorithm 

	@Override
	public List<State<T>> search(Searchable<T> s)
	{
		//clearOpenList();
		State<T> initState = new State<T>(s.getInitialState());
		initState.setCost(0);
		addToOpenList(initState);
		LinkedHashSet<State<T>> closedSet = new LinkedHashSet<>();
		while(!isOpenListEmpty())
		{

			State<T> n = popOpenList();
			closedSet.add(n);
			
			if(s.isGoal(n))
				return backTrace(n); // getting the path from n backwards to the initial state 
			
			List<State<T>> successors=s.getAllPossibleStates(n);
			
			for(State<T> state : successors)
			{
				if(!closedSet.contains(state) && !isContainsInOpenList(state))
				{
					state.setCameFrom(n);
					addToOpenList(state);
				}
				else if(!closedSet.contains(state) &&
						(n.getCost() // the cost Path until State n
							+
						state.getCost() // the real cost of neighbor state
							) < findInOpenList(state).getCost()) // the path cost of the neighbor state
				{	
					
					removeFromOpenList(state);
					addToOpenList(state); // remember: addToOpenList adds the predecessor's path cost + the current state's cost
				}
			}
		}
		
		return null; // means the BestFS couldn't find any solution
	}
}


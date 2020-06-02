package server_side;

import java.util.ArrayList;
import java.util.HashSet;

public class BFS<Solution> extends CommonSearcher<Solution> {

	@Override
	public Solution search(Searchable s) 
	{
		openList.add(s.getInitialState());
		HashSet<State> closedSet=new HashSet<State>();
		while(openList.size()>0)
		{
			State n=popOpenList();// dequeue
			closedSet.add(n);
			ArrayList<State> successors=s.getAllPossibleStates(n); //however it is implemented
			if(n.equals(s.getGoalState()))
				return backTrace(n, s.getInitialState());
				// private method, back traces through the parents
			for(State state : successors){
				if(!closedSet.contains(state) && ! openList.contains(state)){
					state.setCameFrom(n);
					openList.add(state);
				}
				else if(n.getCost()+(state.getCost()-state.getCameFrom().getCost())<state.getCost()) 
					 	if(openList.contains(state))
					 		state.setCameFrom(n);
						else  {
							state.setCameFrom(n);
							closedSet.remove(state);
							openList.add(state);
					}
			}
		}
		return backTrace(s.getGoalState(), s.getInitialState());
	}



	@Override
	public int getNumberOfNodesEvaluated() {
		return evluateNodes;
	}
	
}

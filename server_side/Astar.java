package server_side;

import java.util.ArrayList;
import java.util.HashSet;

public class Astar<Solution,heuristic> extends CommonSearcher<Solution> {
	
	
	public interface Heuristic{
		public double cost(State s,State goalState);
	}
	Heuristic h;
	public Astar(Heuristic h) {
		this.h=h;
	}
	@Override
	public Solution search(Searchable s) {
		double nCost,stateCost;
		openList.add(s.getInitialState());
		HashSet<State> closedSet=new HashSet<State>();
		while(openList.size()>0)
		{
			State n=popOpenList();// dequeue
			closedSet.add(n);
			ArrayList<State> successors=s.getAllPossibleStates(n); //however it is implemented
			n.setCost(n.getCost()+h.cost(n,s.getGoalState()));
			if(n.equals(s.getGoalState()))
				return backTrace(n, s.getInitialState());
				// private method, back traces through the parents
			for(State state : successors){
				state.setCost(state.getCost()+h.cost(state,s.getGoalState()));
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

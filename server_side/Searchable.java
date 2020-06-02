package server_side;

import java.util.ArrayList;

public interface Searchable {
	State getInitialState();
	State getGoalState();
	ArrayList<State> getAllPossibleStates(State S);
}

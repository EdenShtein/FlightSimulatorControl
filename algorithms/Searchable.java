package algorithms;

import java.util.List;

public interface Searchable<T> { //interface for out Search problem. in our case it will be the matrix problem
	public State<T> getInitialState();
	public boolean isGoal(State<T> s);
	List<State<T>> getAllPossibleStates(State<T> s);
}

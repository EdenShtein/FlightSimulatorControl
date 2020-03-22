package server_side;

import algorithms.Searchable;
import algorithms.Searcher;

public class SearcherSolver <Problem,Solution,StateType> implements Solver<Problem, Solution> { //Searcher solver implements the solver interface
	private Searcher<StateType> s;
	
	public SearcherSolver(Searcher<StateType> s)
	{
		this.s=s;
	}
	@Override
	 public Solution solve(Problem p) //Solve the matrix problem	
	 {
		
		return (Solution) s.search((Searchable<StateType>) p);
	 }
}

package server_side;

public class SolverSearcher<Problem,Solution> implements Solver<Problem, Solution> {
	private Searcher s;
	public SolverSearcher(Searcher s) {
		this.s=s;
	}
	@Override
	public Solution Solve(Problem p) {
		return (Solution) s.search((Searchable) p);
	}

}

package server_side;

public interface CacheManager<Problem, Solution> {
	public boolean isSolutionCached(Problem p);
	public Solution getSolution(Problem p);
	public void saveSolution(Problem p, Solution s) throws Exception;
}

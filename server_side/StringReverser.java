package server_side;

public class StringReverser<Problem, Solution> implements Solver<Problem, Solution> {
	@Override
	public Solution solve(Problem p)
	{
		StringBuilder newString=new StringBuilder();
		newString.append(p);
		newString.reverse();
		return (Solution) newString.toString();
	}
}

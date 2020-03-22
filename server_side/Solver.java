package server_side;

public interface Solver  <Problem,Solution>{
	public Solution solve (Problem p);
}

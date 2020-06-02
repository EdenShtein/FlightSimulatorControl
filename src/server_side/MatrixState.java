package server_side;



public class MatrixState extends State<String> {

	//private String state;
	public MatrixState(String state) {
		super(state);
		this.setCameFrom(null);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		return result;
	}
	@Override
	public boolean equals(State s) {
		if(this.state.intern()==((String) s.getState()).intern())
			return true;
		return false;
	}

	
	
	
	
}

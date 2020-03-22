package algorithms;

public class State <T>{ //Represent the State of the actual state of the world
	private T state; // description of this state
	private double cost; // cost to reach this state.
	private State<T> cameFrom; // the state we came from to this state.
	
	public State() {
		cost = 0;
		cameFrom = null;
	}
	public State( T state) //CTOR
	{
		this.state=state;
		this.cost=0;
		this.cameFrom=null;
	}
	public State(State<T> other)
	{
		this.state = other.getState();
		this.cost = other.getCost();
		this.cameFrom = other.getCameFrom();
	}
	
	public State(T state, double cost)
	{
		this.state = state;
		this.cost = cost;
		cameFrom = null;
	}
	

	// State must override equals method,first of all override object method and then write a new one
	
	@Override
	public boolean equals(Object obj) {
		return state.equals(((State<T>)obj).state);
	}
	
	public boolean equals(State<T> s)
	{
		return this.state.equals(s.state);
	}
	
	// Note: <T> must override toString() and equals() method so it will work properly. 
	@Override
	public int hashCode() 
	{
		return (state + "").hashCode();
	}
		
	//Getters and Setters
	public T getState() {
		return state;
	}
	public void setState(T state) {
		this.state = state;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public State<T> getCameFrom() {
		return cameFrom;
	}
	public void setCameFrom(State<T> cameFrom) {
		this.cameFrom = cameFrom;
	}
	
	
	

}

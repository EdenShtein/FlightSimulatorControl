package algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class MatrixProblem implements Searchable<Position> {
	int[][] costs;
	int width; // Width of the matrix ( from 1 to n )
	int height; // Height of the matrix ( from 1 to n )
	State<Position> start;
	HashSet<State<Position>> goals;
	//CTOR
	public  MatrixProblem()
	{
		this.costs=null;
		this.width=0;
		this.height=0;
		start = null;
		goals = null;	
	}
	
	public MatrixProblem(int[][] costs, int width, int height, Position start, List<Position> goals) {
		this.costs = costs;
		this.width = width;
		this.height = height;
		this.start = new State(start, costs[start.getRow()][start.getCol()]);
		
		this.goals = new HashSet<>();
		for (Position position : goals)
			this.goals.add(new State(position, costs[position.getRow()][position.getCol()]));
	}
	
	
	
	
	//Override methods of Searchable
	
	@Override
	public State<Position> getInitialState() {
		return this.start;
	}
	
	@Override
	public boolean isGoal(State<Position> s) {
		return this.goals.contains(s);
	}
	
	@Override
	public List<State<Position>> getAllPossibleStates(State<Position> s) { //Method that returns all possible option from our current position
		Position StatePosition = s.getState();
		ArrayList<State<Position>> neighbors = new ArrayList<>();
		
		if(StatePosition.getCol() != 0)
		{
			neighbors.add(
					new State(
							new Position(StatePosition.getRow(), StatePosition.getCol() - 1),
							costs[StatePosition.getRow()][StatePosition.getCol() - 1]));
		}
		
		if(StatePosition.getRow() != 0)
		{
			neighbors.add(
					new State(
							new Position(StatePosition.getRow() - 1, StatePosition.getCol()),
							costs[StatePosition.getRow() - 1][StatePosition.getCol()]));
		}
		
		if(StatePosition.getCol() != width - 1)
		{
			neighbors.add(
					new State(
							new Position(StatePosition.getRow(), StatePosition.getCol() + 1),
							costs[StatePosition.getRow()][StatePosition.getCol() + 1]));
		}
		
		if(StatePosition.getRow() != height - 1)
		{
			neighbors.add(
					new State(
							new Position(StatePosition.getRow() + 1, StatePosition.getCol()),
							costs[StatePosition.getRow() + 1][StatePosition.getCol()]));
		}
		
		return neighbors;
	}
	
	public String[] getDirection(List<State<Position>> pathList) //method that returs the way we need to move 
	{
		LinkedList<String> paths = new LinkedList<>();
		
		for (State<Position> p : pathList)
		{
			if(p.getCameFrom() == null)
				break;
			
			if(p.getState().getCol() > p.getCameFrom().getState().getCol())
				paths.add("Right");
			else if(p.getState().getCol() < p.getCameFrom().getState().getCol())
				paths.add("Left");
			else if(p.getState().getRow() > p.getCameFrom().getState().getRow())
				paths.add("Down");
			else if(p.getState().getRow() < p.getCameFrom().getState().getRow())
				paths.add("Up");
		}
		
		Collections.reverse(paths);
		return (String[]) paths.toArray(new String[0]);
	}
	

	//Getters and Setters
	public int[][] getCosts() {
		return costs;
	}


	public void setCosts(int[][] costs) {
		this.costs = costs;
	}


	public int getWidth() {
		return width;
	}


	public void setWidth(int width) {
		this.width = width;
	}


	public int getHeight() {
		return height;
	}


	public void setHeight(int height) {
		this.height = height;
	}


	public State<Position> getStart() {
		return start;
	}


	public void setStart(State<Position> start) {
		this.start = start;
	}


	public HashSet<State<Position>> getGoals() {
		return goals;
	}


	public void setGoals(HashSet<State<Position>> goals) {
		this.goals = goals;
	}
}

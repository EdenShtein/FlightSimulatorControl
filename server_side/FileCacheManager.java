package server_side;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;

public class FileCacheManager<Problem, Solution> implements CacheManager<Problem, Solution> {
	private HashMap<Problem, Solution> map;
	
	public FileCacheManager()
	{
		this.map=new HashMap<>();
	}
	
	public HashMap<Problem, Solution> getMap() {
		return map;
	}

	public void setMap(HashMap<Problem, Solution> map) {
		this.map = map;
	}
	
	@Override
	public boolean isSolutionCached(Problem p)
	{
		return (map.get(p) != null);
	}
	@Override
	public Solution getSolution(Problem p)
	{
		return map.get(p);
	}
	@Override
	public void saveSolution(Problem p, Solution s) throws Exception
	{
		//If the solution already exist don't do nothing
		if(isSolutionCached(p))
			return;
		map.put(p,s);
	}

}

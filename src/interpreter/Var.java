package interpreter;

import java.util.Observable;
import java.util.Observer;

public class Var extends Observable implements Observer {
	double value;
	String name;
	String location;

	@Override
	public void update(Observable o, Object arg) {
		Double d=new Double(0);
		if(arg.getClass()==(d.getClass()))
			if(this.value!=(double)arg) {
				this.setV((double) arg);
				this.setChanged();
				this.notifyObservers(arg+"");
			}


	}

	@Override
	public String toString() {
		return this.location;
	}

	public Var(double v) {
		this.value=v;
		this.location=null;
	}
	public Var() {

	}
	public Var(String loc) {
		super();
		location = loc;
	}

	public double getV() {
		return value;
	}

	public void setV(double v) {
		if(this.value!=v) {
			this.value = v;
			setChanged();
			notifyObservers(v);
		}


	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String loc) {
		location = loc;
	}
}

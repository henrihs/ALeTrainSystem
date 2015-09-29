package aletrainsystem.models.railroad;

import aletrainsystem.models.Pair;

public class RailStretch {
	
	private int railStrechId;
	private Pair<Intersection, Intersection> connections;
	private int length;
	
	public RailStretch(int id){
		this.setId(id);
	}
	
	public RailStretch(int id, Intersection start, Intersection end, int length){
		this(id);
		connections = new Pair<Intersection, Intersection>(start, end);
		this.length = length;
	}
	
	public int getId() {
		return railStrechId;
	}
	
	public void setId(int railStrechId) {
		this.railStrechId = railStrechId;
	}
	
	public Pair<Intersection, Intersection> getConnections(){
		return connections;
	}
	
	public void setLenght(int length){
		this.length = length;
	}
	
	public int getLenght(){
		return length;
	}
	
	public int getSleepersCount(){
		return length * 4;
	}

}

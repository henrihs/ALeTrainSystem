package ntnu.no.aletrainsystem.models;

public class PointSwitchId {
	private int id;
	
	public PointSwitchId(int id){
		this.id = id;
	}
	
	public int get(){
		return id;
	}
	
	public void set(int i){
		id = i;
	}
}

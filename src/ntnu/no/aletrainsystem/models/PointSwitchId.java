package ntnu.no.aletrainsystem.models;

public class PointSwitchId implements Comparable {
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

	@Override
	public int compareTo(Object o) {
		if (o.getClass().equals(this.getClass())
			&& ((PointSwitchId)o).get() == get()){
			return 1;
		}
		return 0;
	}
}

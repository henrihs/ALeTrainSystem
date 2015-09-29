package aletrainsystem.models;

public class PointSwitchId implements Comparable<PointSwitchId> {
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
	public int compareTo(PointSwitchId o) {
		if (o.get() == get()){
			return 1;
		}
		return 0;
	}
}

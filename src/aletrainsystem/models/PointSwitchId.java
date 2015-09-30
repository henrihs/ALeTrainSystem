package aletrainsystem.models;

public class PointSwitchId implements Comparable<PointSwitchId> {
	private final int id;
	
	public PointSwitchId(int id){
		this.id = id;
	}
	
	public PointSwitchId(String id){
		this.id = Integer.valueOf(id);
	}
	
	public int value(){
		return id;
	}
	
	@Override
	public boolean equals(Object other){
		return this.value() == ((PointSwitchId)other).value();
	}
	
	@Override
	public String toString(){
		return String.valueOf(id);
	}
	
	@Override
	public int compareTo(PointSwitchId other) {
		return other.value() - value();
	}
}

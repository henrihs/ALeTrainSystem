package aletrainsystem.models;

public class RailPartId implements Comparable<RailPartId> {
	private final long id;
	
	public RailPartId(long id){
		this.id = id;
	}
	
	public RailPartId(String id){
		this.id = Integer.valueOf(id);
	}
	
	public long value(){
		return id;
	}
	
	@Override
	public boolean equals(Object other){
		return this.value() == ((RailPartId)other).value();
	}
	
	@Override
	public String toString(){
		return String.valueOf(id);
	}
	
	@Override
	public int compareTo(RailPartId other) {
		return (int) (other.value() - value());
	}
}

package aletrainsystem.models;

public class Pair<T> {
	
	protected final T first, second;
	
	public Pair(T first, T second){
		this.first = first;
		this.second = second;
	}
	
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Pair<?>)) {
			return false;
		}
		
		Pair<T> pair = (Pair<T>)other;
		
		return this.contains(pair.first) && this.contains(pair.second);
	}
	
	public boolean contains(T object){
		return first.equals(object) || second.equals(object);
	}
	
	public T first(){
		return first;
	}
	
	public T second(){
		return second;
	}
}

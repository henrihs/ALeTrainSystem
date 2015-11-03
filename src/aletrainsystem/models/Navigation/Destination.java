package aletrainsystem.models.Navigation;

public interface Destination {
	public Destination[] getNext(Destination previous);
	public int length();
}

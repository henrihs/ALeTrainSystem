package aletrainsystem.models.Navigation;

import aletrainsystem.models.railroad.RailPart;

public class Position {

	private RailPart[] parts;
	
	public RailPart[] get() {
		return parts;
	}
	
	public void set(RailPart[] parts) {
		this.parts = parts;
	}
}

package aletrainsystem.models.railroad;

public class Intersection {
	private RailStretch entry, divert, through;
	private int pointSwitchId;
	
	public Intersection(int pointSwitchId){
		this.pointSwitchId = pointSwitchId;
	}

	public Intersection(int pointSwitchId, RailStretch entry, RailStretch divert, RailStretch through) {
		this(pointSwitchId);
		this.setEntry(entry);
		this.setDivert(divert);
		this.setThrough(through);
	}
	
	public int getPointSwitchId(){
		return pointSwitchId;
	}
	
	@Override
	public boolean equals(Object other){
		return this.pointSwitchId == ((Intersection)other).getPointSwitchId();
	}

	public RailStretch getEntry() {
		return entry;
	}

	public void setEntry(RailStretch entry) {
		this.entry = entry;
	}

	public RailStretch getDivert() {
		return divert;
	}

	public void setDivert(RailStretch divert) {
		this.divert = divert;
	}

	public RailStretch getThrough() {
		return through;
	}

	public void setThrough(RailStretch through) {
		this.through = through;
	}
}

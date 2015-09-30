package aletrainsystem.models.railroad;

import aletrainsystem.enums.TrackStatus;
import aletrainsystem.models.ConnectorPair;
import aletrainsystem.models.railroad.Intersection.IntersectionConnector;

public class Track {
	
	private ConnectorPair connectors;
	private TrackId trackId;
	private int length;
	private TrackStatus status;
		
	public Track(IntersectionConnector connector1, IntersectionConnector connector2, int length){
		this.length = length;
		addConnectors(connector1, connector2);
		trackId = new TrackId(connector1, connector2);
		status = TrackStatus.NORMAL;
	}
	
	public TrackId getId() {
		return trackId;
	}
		
	public ConnectorPair getConnectors(){
		return connectors;
	}
	
	private void addConnectors(IntersectionConnector connector1, IntersectionConnector connector2){
		connectors = new ConnectorPair(connector1, connector2);
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
	
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Track)) {
			return false;
		}
		
		Track track = (Track) other;
		
		return (trackId.equals(track.trackId) && length == track.getLenght());
	}

	public TrackStatus getStatus() {
		return status;
	}

	public void setStatus(TrackStatus status) {
		this.status = status;
	}
}

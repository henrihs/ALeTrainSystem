package aletrainsystem.models.railroad;

import aletrainsystem.enums.TrackStatus;
import aletrainsystem.models.ConnectorPair;
import aletrainsystem.models.railroad.PointSwitch.PointSwitchConnector;

public class RailLeg {
	
	private ConnectorPair connectors;
	private RailLegId trackId;
	private int length;
	private TrackStatus status;
		
	public RailLeg(PointSwitchConnector connector1, PointSwitchConnector connector2, int length){
		this.length = length;
		addConnectors(connector1, connector2);
		trackId = new RailLegId(connector1, connector2);
		status = TrackStatus.NORMAL;
	}
	
	public RailLegId getId() {
		return trackId;
	}
		
	public ConnectorPair getConnectors(){
		return connectors;
	}
	
	private void addConnectors(PointSwitchConnector connector1, PointSwitchConnector connector2){
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
		if (!(other instanceof RailLeg)) {
			return false;
		}
		
		RailLeg track = (RailLeg) other;
		
		return (trackId.equals(track.trackId) && length == track.getLenght());
	}

	public TrackStatus getStatus() {
		return status;
	}

	public void setStatus(TrackStatus status) {
		this.status = status;
	}
}

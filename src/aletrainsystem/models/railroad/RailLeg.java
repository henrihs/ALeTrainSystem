package aletrainsystem.models.railroad;

import java.util.ArrayList;

import aletrainsystem.enums.TrackStatus;
import aletrainsystem.models.Navigation.Destination;
import aletrainsystem.models.railroad.PointSwitchConnector;

public class RailLeg implements Destination {
	
	private ConnectorPair connectors;
	private RailLegId trackId;
	private int length;
	private ArrayList<RailPiece> railBricks;
	private TrackStatus status;
	
	public RailLeg() {
		status = TrackStatus.UNDER_CONSTRUCTION; 
	}
		
	public RailLeg(PointSwitchConnector connector1, PointSwitchConnector connector2){
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
		if (connector1 != null) {
			connector1.setConnectedRailLeg(this);
		}
		
		if (connector2 != null) {
			connector2.setConnectedRailLeg(this);
		}
	}
	
	public int getLenght(){
		return railBricks.size();
	}
	
	public int getSleepersCount(){
		int sleepers = 0;
		for (RailPiece railBrick : railBricks) {
			sleepers += railBrick.sleepers();
		}
		
		return sleepers;
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

	public void addRailPiece(RailPiece railBrick) {
		railBricks.add(railBrick);
	}
}

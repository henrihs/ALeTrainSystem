package aletrainsystem.traincontroller;

import aletrainsystem.mapcontroller.MapInitParams;
import aletrainsystem.models.TrainId;
import aletrainsystem.models.railroad.Railroad;
import aletrainsystem.models.railroad.RailroadBuilder;
import no.ntnu.item.arctis.runtime.Block;

public class TrainController extends Block {
	
	public aletrainsystem.models.TrainId id;
	public aletrainsystem.models.railroad.IRailroad railroad;
	public java.util.Set<aletrainsystem.models.TrainId> trainsInProximity;
	public void logAndThrow(String errorMessage) {
		logger.error(errorMessage);
	}
	
	
	public void setInitParamsFromProperties() {
		id = new TrainId((String) getProperty(TrainId.ID_KEY));
		RailroadBuilder builder = new RailroadBuilder();
		railroad = builder.build((String) getProperty(Railroad.PROPERTYNAME));
	}


	public MapInitParams getMapInitParams() {
		int sizeOfVessel = Integer.valueOf((String) getProperty(TrainId.VESSEL_SIZE_KEY));
		return new MapInitParams(railroad, sizeOfVessel);
	}


	public void addToTrainSet() {
	}


	public void addToProximitySet(TrainId train) {
		trainsInProximity.add(train);
	}


	public void removeFromProximitySet(TrainId train) {
		trainsInProximity.remove(train);
	}
}

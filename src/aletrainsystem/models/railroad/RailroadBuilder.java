package aletrainsystem.models.railroad;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import bluebrick4j.conversion.BbmParser;
import bluebrick4j.model.Brick;
import bluebrick4j.model.BrickType;
import bluebrick4j.model.Connexion;
import bluebrick4j.model.Layer;
import bluebrick4j.model.Map;

public class RailroadBuilder {

	private Railroad railroad;
	private HashMap<Connexion, Brick> connexionToBrickMapping;
	private ArrayList<Brick> bricks;
	private LinkedList<Brick> pointSwitchBricks;
	private HashSet<Object> visited;
	private String bbmFilePath;

	public RailroadBuilder(String bbmFilePath){
		railroad = new Railroad();
		this.bbmFilePath = bbmFilePath;
	}
	
	public Railroad getRailroad() {
		convertFromBbmFile();
		return railroad;
	}

	private void convertFromBbmFile() {
		Map map = BbmParser.loadMapFromFile(bbmFilePath);
		bricks = new ArrayList<>();
		pointSwitchBricks = new LinkedList<>();

		for (Layer layer : map.getLayers().getLayers()) {
			if (layer.getBricks() != null) {
				layer.getBricks().getBricks().forEach((b) -> {
					bricks.add(b);
					if (b.getBrickType() == BrickType.LEFTHANDPOINTSWITCH
						|| b.getBrickType() == BrickType.RIGHTHANDPOINTSWITCH){
						pointSwitchBricks.add(b);
					}
				});
			}
		}

		connexionToBrickMapping = ConnectionToBrickMapping(bricks);
		visited = new HashSet<>();

		while (!pointSwitchBricks.isEmpty()){
			Brick brick = pointSwitchBricks.getFirst();
			BrickType type = brick.getBrickType();
			if (visited.contains(brick)) { 
				continue; 
			}
			
			PointSwitch startPoint = railroad.findOrAddPointSwitch(Integer.valueOf(brick.getId()));
			List<Connexion> connections = brick.getConnexions().getConnexions();
			for (int i = 0; i < 3; i++) {
				Connexion nextConnection = connections.get(i).getLinkedTo();
				if (!visited.contains(nextConnection)) {
					PointSwitchConnector startConnector = startPoint.getConnector(ConnectorConverter.convert(type).apply(i));
					RailLeg fullLeg = stepInto(nextConnection, startConnector, 0);
					railroad.addRailLeg(fullLeg);
				}
			}
			
			pointSwitchBricks.removeFirst();
		}
	}

	private RailLeg stepInto(Connexion connexion, PointSwitchConnector startConnector, int currentLength) {
		visited.add(connexion);
		Brick brick = connexionToBrickMapping.get(connexion);
		
		if (brick.getBrickType() == BrickType.STRAIGHT
				|| brick.getBrickType() == BrickType.CURVED) {
			currentLength++;
			
			for (Connexion nextConnexion : brick.getConnexions().getConnexions()) {
				if (nextConnexion != connexion) {
					return stepInto(nextConnexion.getLinkedTo(), startConnector, currentLength);
				}
			}
		}

		PointSwitch endOfLeg = railroad.findOrAddPointSwitch(Integer.valueOf(brick.getId()));
		int index = brick.getConnexions().getConnexions().indexOf(connexion);
		PointSwitchConnector endConnector = endOfLeg.getConnector(ConnectorConverter.convert(brick.getBrickType()).apply(index));

		RailLeg fullLeg = new RailLeg(startConnector, endConnector, currentLength);
		return fullLeg;
	}

	private HashMap<Connexion, Brick> ConnectionToBrickMapping(List<Brick> bricks) {
		HashMap<Connexion, Brick> map = new HashMap<>();
		for (Brick brick : bricks) {
			for (Connexion connection : brick.getConnexions().getConnexions()) {
				map.put(connection, brick);
			}
		}

		return map;
	}
}

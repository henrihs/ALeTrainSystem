package aletrainsystem.models.railroad;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
		railroad.setMd5sum(getMd5Sum(bbmFilePath));
		bricks = new ArrayList<>();
		pointSwitchBricks = new LinkedList<>();

		iterateThroughBricks(map);

		connexionToBrickMapping = ConnectionToBrickMapping(bricks);
		visited = new HashSet<>();

		followAllPointBrickConnections();
	}
	
	private byte[] getMd5sum(String filePath) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try (InputStream is = Files.newInputStream(Paths.get("file.txt"))) {
		  DigestInputStream dis = new DigestInputStream(is, md);
		  /* Read stream to EOF as normal... */
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return md.digest();
	}

	private void iterateThroughBricks(Map map) {
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
	}

	private void followAllPointBrickConnections() {
		while (!pointSwitchBricks.isEmpty()){
			Brick pointBrick = pointSwitchBricks.getFirst();
			if (visited.contains(pointBrick)) { 
				continue; 
			}
			
			followPointBrickConnections(pointBrick);
			
			pointSwitchBricks.removeFirst();
		}
	}
	
	private void followPointBrickConnections(Brick pointBrick) {
		PointSwitch startPoint = railroad.findOrAddPointSwitch(Integer.valueOf(pointBrick.getId()));
		List<Connexion> connections = pointBrick.getConnexions().getConnexions();
		BrickType type = pointBrick.getBrickType();
		for (int i = 0; i < 3; i++) {
			Connexion nextConnection = connections.get(i).getLinkedTo();
			PointSwitchConnector startPointConnector = startPoint.getConnector(ConnectorConverter.convert(type).apply(i));
			if (!railroad.hasRailLegWithConnector(startPointConnector)) {
				RailLeg leg = stepInto(nextConnection, startPointConnector, 0);
				if (leg != null) {
					railroad.addRailLeg(leg);
				}
				else {
					railroad.setRailSystemEntryPoint(startPointConnector);
				}
			}
		}		
	}

	private RailLeg stepInto(Connexion connexion, PointSwitchConnector startConnector, int currentLength) {
		if (connexion == null) {
			return null;
		}
		
		visited.add(connexion);
		Brick brick = connexionToBrickMapping.get(connexion);
		
		if (brick.getBrickType() != BrickType.LEFTHANDPOINTSWITCH
				&& brick.getBrickType() != BrickType.RIGHTHANDPOINTSWITCH) {
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

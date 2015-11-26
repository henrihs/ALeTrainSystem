package aletrainsystem.algorithms;

import java.util.ArrayList;

import aletrainsystem.enums.PointConnectorEnum;
import aletrainsystem.models.navigation.Position;
import aletrainsystem.models.navigation.Route;
import aletrainsystem.models.navigation.RouteElement;
import aletrainsystem.models.railroad.*;
import aletrainsystem.pointswitch.PointConnector;

public class GreedyAlgorithmTests {

	public static void main(String[] args) {
		RailroadBuilder builder = new RailroadBuilder();
		IRailroad railroad = builder.build("resources/maps/ngorongoro2.map");
		ShortestPathUniDirectional greedy = new GreedyAlgorithm();
		
		StartLeg start = railroad.getRailSystemStartLeg();
		
		PointConnector direction = start.getConnector().point().getConnector(PointConnectorEnum.ENTRY);  
		ArrayList<RailComponent> pos = new ArrayList<>();
		start.getStartOfLeg(4).forEach(c -> pos.add(c));
		pos.add(start.getConnector());
		Position position = new Position(pos, 3);
		
		RouteElement station = railroad.getRouteElement("29620820d.62920918d");
		
		Route shortestRoute = greedy.findSingleShortestPath(railroad, position, station, direction);
		
		System.out.println(shortestRoute);
	}
	
		
}

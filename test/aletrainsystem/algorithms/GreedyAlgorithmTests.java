package aletrainsystem.algorithms;

import java.util.ArrayList;

import aletrainsystem.enums.PointConnectorEnum;
import aletrainsystem.models.navigation.Position;
import aletrainsystem.models.navigation.Route;
import aletrainsystem.models.navigation.RouteDescriptor;
import aletrainsystem.models.navigation.RouteElement;
import aletrainsystem.models.railroad.*;
import aletrainsystem.pointswitch.PointConnector;

public class GreedyAlgorithmTests {

	public static void main(String[] args) {
		RailroadBuilder builder = new RailroadBuilder();
		IRailroad railroad = builder.build("resources/maps/ngorongoro2.map");
		GreedyAlgorithm greedy = new GreedyAlgorithm();
		
//		StartLeg start = railroad.getRailSystemStartLeg();
		
//		PointConnector direction = start.getConnector().point().getConnector(PointConnectorEnum.ENTRY);  
		ArrayList<RailComponent> pos = new ArrayList<>();
//		start.getStartOfLeg(4).forEach(c -> pos.add(c));
//		pos.add(start.getConnector());
//		Position position = new Position(pos, 3);
		
		RailLeg start = (RailLeg) railroad.getRouteElement("41383174d.62887372d");
		PointConnector direction = (PointConnector) railroad.getRouteElement("41383174e"); 
		RouteElement station = railroad.getRouteElement("29620820t.62920918t");	
		RouteDescriptor descriptor = new RouteDescriptor(start, station);
		
		Route shortestRoute = null;
		try {
			shortestRoute = greedy.findSingleShortestPath(railroad, descriptor, direction);
		} catch (RouteNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(shortestRoute);
	}
	
		
}

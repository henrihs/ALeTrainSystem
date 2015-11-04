package aletrainsystem.models.railroad;



public class RailroadBuilderTests {
	public static void main(String[] args) {
		Railroad railroad = RailroadBuilder.build("resources/maps/ngorongoro1.map");
	
		
		System.out.println("StartPoint: " + railroad.getRailSystemStartLeg().getConnector().pointSwitch().id() + "." + railroad.getRailSystemStartLeg().getConnector().getType());
		for (RegularLeg leg : railroad.getRailLegs().values()) {
			System.out.println(leg.getId() + (railroad.isStation(leg) ? " (Station)" : ""));
		}
	}
}

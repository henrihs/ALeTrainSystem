package aletrainsystem.models.railroad;

public class RailroadBuilderTests {
	public static void main(String[] args) {
		RailroadBuilder builder = new RailroadBuilder("resources/maps/ngorongoro1.bbm");
		Railroad railroad = builder.getRailroad();
	
		System.out.println("StartPoint: " + railroad.getRailSystemEntryPoint().getPointSwitch().getId() + "." + railroad.getRailSystemEntryPoint().getConnectorType());
		for (RailLeg leg : railroad.getRailLegs().values()) {
			System.out.println(leg.getId() + (railroad.isStation(leg) ? " (Station)" : ""));
		}
	}
}

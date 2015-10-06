package aletrainsystem.models.railroad;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

public class RailLegId {
	
//	public final Logger logger = LoggerFactory.getLogger(this.getClass());
	private static final String PATTERN = "\\d{1,3}[e|t|d]{1}.\\d{1,3}[e|t|d]{1}";
	private static final String DELIMITER = ".";
	private final String stringId;
	
	public RailLegId(String id){
		this.stringId = validate(id);
	}

	public RailLegId(PointSwitchConnector connector1, PointSwitchConnector connector2){
		String idString = buildOrderedIdString(connector1, connector2);
		
		stringId = validate(idString);
	}

	public String value() {
		return stringId;
	}
	
	@Override
	public boolean equals(Object other){
		if (other.getClass() != this.getClass()){
			return false;
		}
		
		String[] o = other.toString().split(DELIMITER);
		String[] t = this.toString().split(DELIMITER);
		if (t[0].equals(o[0]) && t[1].equals(o[1])
			|| t[0].equals(o[1]) && t[1].equals(o[0])) {
			return true;
		}
		
		return false;
	}
	
	@Override
	public String toString(){
		return stringId;
	}
	
	private String buildOrderedIdString(PointSwitchConnector connector1, PointSwitchConnector connector2) {
		String idString = "";
		String idString1 = "";
		idString1.concat(connector1.getIntersection().getPointSwitchId().toString());
		idString1.concat(connector1.getConnectorType().shorthand());
		String idString2 = "";
		idString2.concat(connector2.getIntersection().getPointSwitchId().toString());
		idString2.concat(connector2.getConnectorType().shorthand());
		
		if (connector1.getIntersection().getPointSwitchId().compareTo(
				connector2.getIntersection().getPointSwitchId()) > 0) {
			idString.concat(idString1).concat(DELIMITER).concat(idString2);
		}
		else if (connector1.getIntersection().getPointSwitchId().compareTo(
				connector2.getIntersection().getPointSwitchId()) < 0) {
			idString.concat(idString2).concat(DELIMITER).concat(idString1);
		}
//		else {
//			logger.warn("TrackId can not consist of two identical pointSwitchIds(".
//					concat(idString1).
//					concat(" and ").
//					concat(idString2).
//					concat(")"));
//		}
		return idString;
	}
	
	private String validate(String stringId) {
		String s = "";
		if (stringId.matches(PATTERN)) {			
			s = stringId;
		}
//		else {
//			logger.warn("TrackId pattern does not match requirements (".
//					concat(stringId).
//					concat(")"));
//		}
		return s;
	}
}

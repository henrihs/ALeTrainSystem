package ntnu.no.aletrainsystem.pointswitchcontroller;

import no.ntnu.item.arctis.runtime.Block;

public class PointSwitchController extends Block {

	public void logAndThrow(String errorMessage) throws Exception {
		logger.error(errorMessage);
		throw new Exception(errorMessage);
	}

	public String getInitParameters() {
		for (Object s : getProperty(key)) {
			
		}
	}

}

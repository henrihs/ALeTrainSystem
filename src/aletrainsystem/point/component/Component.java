package aletrainsystem.point.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.rabbitmq.client.impl.AMQBasicProperties;

import aletrainsystem.enums.MotorPort;
import no.ntnu.item.arctis.runtime.Block;

public class Component extends Block {

	public void logAndThrow(String errorMessage) {
		logger.error(errorMessage);
	}

	public HashMap<String, String> prepareCommParams() {
		HashMap<String, String> params = new HashMap<>();
		
		params.put("USERNAME", (String) getProperty("USERNAME"));
		params.put("PASSWORD", (String) getProperty("PASSWORD"));
		params.put("HOSTNAME", (String) getProperty("HOSTNAME"));
		params.put("EXCHANGE_NAME", (String) getProperty("EXCHANGE_NAME"));
		
		return params;
	}

	public List<String> getTopics() {
		List<String> topics = new ArrayList<String>();
		topics.add("common.*");
		for (MotorPort motorPort : MotorPort.values()) {
			if (this.hasProperty(motorPort.getPropertyName())){
				String pointId = (String) this.getProperty(motorPort.getPropertyName());
				if (Integer.valueOf(pointId) != 0) {
					topics.add("points.".concat(pointId).concat(".*"));
				}
			}
		}
		
		return topics;
	}

	public void logWarning(String e) {
		logWarn(e);
	}

}

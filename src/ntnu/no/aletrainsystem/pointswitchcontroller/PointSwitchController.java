package ntnu.no.aletrainsystem.pointswitchcontroller;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import no.ntnu.item.arctis.runtime.Block;
import ntnu.no.aletrainsystem.enums.MotorPort;
import ntnu.no.aletrainsystem.models.MotorPortMapping;
import ntnu.no.aletrainsystem.models.PointSwitchId;
import ntnu.no.aletrainsystem.pointswitch.PointSwitchOrder;

public class PointSwitchController extends Block {

	public HashSet<MotorPortMapping> motorPortMappings;
	public java.util.Iterator<MotorPortMapping> mappings;

	public void logAndThrow(String errorMessage) throws Exception {
		logger.error(errorMessage);
		throw new Exception(errorMessage);
	}

	public Set<MotorPortMapping> getMotorPortMappings() {
		Set<MotorPortMapping> mapping = new HashSet<MotorPortMapping>();
		for (MotorPort motorPort : MotorPort.values()) {
			if (this.hasProperty(motorPort.getPropertyName())){
				PointSwitchId id = new PointSwitchId((int)this.getProperty(motorPort.getPropertyName()));
				motorPortMappings.add(new MotorPortMapping(motorPort, id));
			}
		}
		return mapping;
	}

	public Iterator<MotorPortMapping> iterateMotorPorts() {
		return motorPortMappings.iterator();
	}

	public MotorPort getNextMotorPort(Iterator<MotorPortMapping> iterator) {
		if (iterator.hasNext()){
			return iterator.next().getPort();
		}
		else return null;
	}

	public PointSwitchOrder appendMotorPort(PointSwitchOrder order) throws Exception {
		MotorPort port = null;
		for (MotorPortMapping motorPortMapping : motorPortMappings) {
			if (motorPortMapping.getId().equals(order.getPointSwitchId()))
				port = motorPortMapping.getPort();
		}
		
		try {
			order.setMotorPort(port);
		} catch (NullPointerException e) {
			logAndThrow("Got order for unmapped PointSwitchId: ".concat(String.valueOf(order.getPointSwitchId().get())));
		}
		
		return order;
	}

}

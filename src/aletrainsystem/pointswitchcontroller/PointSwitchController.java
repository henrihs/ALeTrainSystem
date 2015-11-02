package aletrainsystem.pointswitchcontroller;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import aletrainsystem.enums.MotorPort;
import aletrainsystem.models.MotorPortMapping;
import aletrainsystem.models.RailPartId;
import aletrainsystem.models.PointSwitchOrder;
import no.ntnu.item.arctis.runtime.Block;

public class PointSwitchController extends Block {

	public java.util.Iterator<MotorPortMapping> mappings;
	public java.util.Set<aletrainsystem.models.MotorPortMapping> motorPortMappings;

	public void logAndThrow(String errorMessage) {
		logger.error(errorMessage);
	}

	public Set<MotorPortMapping> readMotorPortMappings() {
		Set<MotorPortMapping> mapping = new HashSet<MotorPortMapping>();
		for (MotorPort motorPort : MotorPort.values()) {
			if (this.hasProperty(motorPort.getPropertyName())){
				int id = Integer.valueOf((String) this.getProperty(motorPort.getPropertyName()));
				mapping.add(new MotorPortMapping(motorPort, new RailPartId(id)));
			}
		}
		return mapping;
	}

	public Iterator<MotorPortMapping> iterateMotorPorts() {
		return motorPortMappings.iterator();
	}

	public MotorPort getNextMotorPort(Iterator<MotorPortMapping> iterator) {
		if (iterator.hasNext()){
			MotorPortMapping next = iterator.next();
			logger.info(String.format("Initializing pointswitch on port %s with ID %d", next.getPort().name(), next.getPointSwitchId()));
			return next.getPort();
		}
		else return null;
	}

	public PointSwitchOrder appendMotorPort(PointSwitchOrder order) {
		MotorPort port = null;
		for (MotorPortMapping motorPortMapping : motorPortMappings) {
			if (motorPortMapping.getPointSwitchId().equals(order.getPointSwitchId())){
				port = motorPortMapping.getPort();
				order.setMotorPort(port);
				logger.info(String.format("Routing order with state %s to pointswitch on port %s with ID %d", order.getSwitchState(), port, order.getPointSwitchId()));
				return order;
			}			
		}
		
		logger.warn("Got order for unmapped PointSwitchId: ".concat(String.valueOf(order.getPointSwitchId())));
		return order;
	}

}

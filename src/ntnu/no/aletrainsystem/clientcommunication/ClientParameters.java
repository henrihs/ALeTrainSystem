package ntnu.no.aletrainsystem.clientcommunication;

import java.net.Inet4Address;
import java.util.Set;

public class ClientParameters {
	private Inet4Address brokerAddress;
	private String[] queues;
	
	public ClientParameters(Inet4Address brokerAddress, String[] queues){
		this.brokerAddress = brokerAddress;
		queues = queues;
	}
	
	public Inet4Address getBrokerAddress(){
		return this.brokerAddress;
	}
	
	public String[] getQueues(){
		return this.queues;
	}
}

package aletrainsystem.messageunwrapper;

import com.google.gson.Gson;
import com.rabbitmq.client.Envelope;

import aletrainsystem.models.locking.Request;
import aletrainsystem.models.locking.Response;
import aletrainsystem.models.messaging.GreetingMessage;
import aletrainsystem.models.messaging.JoinMessage;
import no.ntnu.item.arctis.runtime.Block;
import ntnu.no.rabbitamqp.util.Message;

public class MessageUnwrapper extends Block {
	
	public java.lang.Object parsedMessage;
	public ntnu.no.rabbitamqp.util.Message message;

	public boolean isJoinMessage() {
		return parsedMessage instanceof JoinMessage;
	}
	
	public boolean isGreetingMessage() {
		return parsedMessage instanceof GreetingMessage;
	}
	
	public boolean isRequest() {
		return parsedMessage instanceof Request;
	}
	
	public boolean isResponse() {
		return parsedMessage instanceof Response;
	}
	
	public Object parseMessage(Message message) {
		String json = message.getJsonBody();
		String className = getClassNameFromEnvelope(message.getEnvelope());

		Gson g = new Gson();
		switch (className) {
		case "JoinMessage":
			parsedMessage = g.fromJson(json, JoinMessage.class);
			break;
		case "GreetingMessage":
			parsedMessage = g.fromJson(json, GreetingMessage.class);
			break;
		case "Request":
			parsedMessage = g.fromJson(json, Request.class);
			break;
		case "Response":
			parsedMessage = g.fromJson(json, Response.class);
			break;
		default:
			parsedMessage = null;
			break;
		}
		
		return parsedMessage;
	}
	
	public JoinMessage castToJoinMessage(Object o) {
		return (JoinMessage)o;
	}
	
	public GreetingMessage castToGreetingMessage(Object o) {
		return (GreetingMessage)o;
	}
	
	public Request castToRequest(Object o) {
		return (Request)o;
	}
	
	public Response castToResponse(Object o) {
		return (Response)o;
	}

	private String getClassNameFromEnvelope(Envelope envelope) {
		String s = envelope.getRoutingKey();
		String[] a = s.split(".");
		return a[a.length-1];
	}

	public void logError() {
		logWarn("Could not interprete type for JSON: ".
				concat(message.getJsonBody()).
				concat(", should be of type ").
				concat(getClassNameFromEnvelope(message.getEnvelope())));
	}
}
package aletrainsystem.messagewrapper;

import aletrainsystem.models.locking.Request;
import aletrainsystem.models.locking.Response;
import aletrainsystem.models.messaging.GreetingMessage;
import aletrainsystem.models.messaging.JoinMessage;
import no.ntnu.item.arctis.runtime.Block;
import ntnu.no.rabbitamqp.util.Message;

public class MessageWrapper extends Block {

	public Message joinToAmqpMessage(JoinMessage join) {
		return new Message("trains.common.".concat(join.getClass().toString()), join);
	}

	public Message GreetingToAmqpMessage(GreetingMessage greeting) {
		return new Message("trains.".
							concat(greeting.entrant().toString()).
							concat(greeting.getClass().toString()), greeting);
	}

	public Message RequestToAmqpMessage(Request request) {
		return new Message("trains.common.".concat(request.getClass().toString()), request);
	}

	public Message ResponseToAmqpMessage(Response response) {
		return new Message("trains.".
							concat(response.collector().toString().
							concat(response.getClass().toString())), response);
	}

}

package com.example;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class HelloActor extends UntypedActor {

    final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    /**
     * Mandatory method to be overridden. This method defines the behavior of
     * the actor.
     */
    @Override
    public void onReceive(Object message) throws Exception {
	log.debug(getSelf().path() + " Received " + message);

	HelloResponse response = null;

	if (message instanceof HelloRequest) {
	    response = sayHello(((HelloRequest) message).message);
	}

	getSender().tell(response, getSelf());
    }

    /**
     * One of the behavior supported by this actor.
     * 
     * @param msg
     * @return
     */
    private HelloResponse sayHello(String msg) {
	System.out.println("Executing sayHello" + msg);
	return new HelloResponse("Hello " + msg);
    }
}

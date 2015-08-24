package com.example;

import scala.concurrent.Await;
import scala.concurrent.Future;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.util.Timeout;

import com.typesafe.config.ConfigFactory;

public class MainApplication {
    public static void main(String[] args) throws Exception {
	// Initialize Actor system: As the first step before doing anything with
	// actors.
	// First argument: Name of actor system.
	// Second argument: Config file name [default application.conf,
	// application.json, application.properties]
	// For custom name of conf file use
	// ConfigFactory.load("name-of-conf-file-without-extension")
	final ActorSystem system = ActorSystem.create(
		"Akka-Single-Node-Example", ConfigFactory.load());
	// This will create an actor with path: /user/hello
	final ActorRef helloActor = system.actorOf(
		Props.create(HelloActor.class), "hello");

	// Fire and forget example. In this scenario we don't get any response.
	// Asynchronous call.
	// Sender is not actor thats why sender = null
	helloActor.tell(new HelloRequest("Vaibhav"), null);

	// If we need response we use Patterns.ask
	// It returns a Sacal Future
	Future<Object> response = Patterns.ask(helloActor, new HelloRequest(
		"Vaibhav"), 10);

	// Here we are blocking to get the future.
	// There are ways by which we can perform tasks in non blocking manner:
	// with the help of callback defined on scala future.
	HelloResponse res = (HelloResponse) Await.result(response, new Timeout(
		5000l).duration());

	System.out.println("Final Response:: " + res.respMessage);
	// Shutdown the actor system. If we don't shutdown actorSystem out main
	// method will not exit.
	system.shutdown();
    }

}

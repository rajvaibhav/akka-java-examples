package sample;

import static sample.SpringExtension.SpringExtProvider;

import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.FiniteDuration;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.pattern.Patterns;

/**
 * A main class to start up the application.
 */
public class Main {

    public static void main(String[] args) throws Exception {

	// create a spring context and scan the classes
	AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
	ctx.scan("sample");
	ctx.refresh();

	// get hold of the actor system
	ActorSystem system = ctx.getBean(ActorSystem.class);

	// use the Spring Extension to create props for a named actor bean
	ActorRef personService = system.actorOf(SpringExtProvider.get(system)
		.props("PersonActor"), "personService");

	Future<Object> createFuture = Patterns.ask(personService,
		"Save Person", 1000);

	Future<Object> findFuture = Patterns.ask(personService, 0, 1000);

	// print the result
	FiniteDuration duration = FiniteDuration.create(3, TimeUnit.SECONDS);

	try {
	    System.out.println(Await.result(createFuture, duration));
	    System.out.println(Await.result(findFuture, duration));
	} catch (Exception e) {
	    System.err.println("Failed getting result: " + e.getMessage());
	    throw e;
	} finally {
	    system.shutdown();
	    system.awaitTermination();
	}
    }
}

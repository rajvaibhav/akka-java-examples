package com.example;

import static com.example.SpringExtension.SpringExtProvider;

import javax.inject.Inject;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

@Configuration
public class ActorSystemConfiguration {

    @Inject
    private ApplicationContext applicationContext;

    @Bean
    public ActorSystem actorSystem() {
	ActorSystem system = ActorSystem.create("AkkaJavaSpring");
	// initialize the application context in the Akka Spring Extension
	SpringExtProvider.get(system).initialize(applicationContext);
	return system;
    }

    @Bean(name = "findCustomerService")
    public ActorRef getFindCustomerService() {
	final ActorRef findCustomerService = actorSystem()
		.actorOf(
			SpringExtProvider.get(actorSystem()).props(
				"FindCustomerService"), "FindCustomerService");
	return findCustomerService;
    }
    
    @Bean(name = "findAllCustomersService")
    public ActorRef getFindAllCustomersService() {
	final ActorRef findCustomerService = actorSystem()
		.actorOf(
			SpringExtProvider.get(actorSystem()).props(
				"FindAllCustomersService"), "FindAllCustomersService");
	return findCustomerService;
    }
    
    @Bean(name = "createNewCustomerService")
    public ActorRef getCreateNewCustomerService() {
	final ActorRef findCustomerService = actorSystem()
		.actorOf(
			SpringExtProvider.get(actorSystem()).props(
				"CreateNewCustomerService"), "CreateNewCustomerService");
	return findCustomerService;
    }
}

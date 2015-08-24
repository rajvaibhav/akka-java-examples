package sample;

import static sample.SpringExtension.SpringExtProvider;

import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import akka.actor.ActorSystem;

import com.mongodb.MongoClientURI;

/**
 * The application configuration.
 */
@Configuration
@EnableMongoRepositories(basePackages = { "sample.repositories" })
class AppConfiguration {

    // the application context is needed to initialize the Akka Spring Extension
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * Actor system singleton for this application.
     */
    @Bean
    public ActorSystem actorSystem() {
	ActorSystem system = ActorSystem.create("AkkaJavaSpring");
	// initialize the application context in the Akka Spring Extension
	SpringExtProvider.get(system).initialize(applicationContext);
	return system;
    }

    @Bean(name = { "mongoTemplate" })
    public MongoTemplate getMongoTemplate() throws UnknownHostException {
	MongoTemplate mongoTemplate = new MongoTemplate(getMongoDbFactory());
	return mongoTemplate;
    }

    @Bean(name = { "mongoDbFactory" })
    public MongoDbFactory getMongoDbFactory() throws UnknownHostException {
	return new SimpleMongoDbFactory(getMongoClientURI());
    }

    public MongoClientURI getMongoClientURI() throws UnknownHostException {
	return new MongoClientURI("mongodb://localhost:27017/akkaspring");
    }
}

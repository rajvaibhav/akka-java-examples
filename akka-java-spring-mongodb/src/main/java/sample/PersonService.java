package sample;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import sample.repositories.PersonRepository;
import scala.concurrent.Future;
import akka.actor.UntypedActor;
import akka.dispatch.Futures;
import akka.dispatch.Mapper;
import akka.pattern.Patterns;

@Named("PersonActor")
@Scope("prototype")
public class PersonService extends UntypedActor {

    // the service that will be automatically injected
    final PersonRepository personRepository;

    @Inject
    public PersonService(
	    @Named("personRepository") PersonRepository personRepository) {
	this.personRepository = personRepository;
    }

    @Override
    public void onReceive(Object arg0) throws Exception {
	if (arg0 instanceof String) {
	    Person per = new Person();
	    per.setFirstName("XYZ");
	    per.setLastName("ABC");
	    Address adr = new Address(3673, "202", "Sector 71", "Gurgaon");
	    per.setAddress(adr);
	    /* This call will run in different thread. */
	    Future<Boolean> futureResult = Futures.future(() -> {
		personRepository.save(per);
		return Boolean.TRUE;
	    }, getContext().dispatcher());
	    /*
	     * Convert future of Boolean to future of String in callback. This
	     * code executes when Future<Boolean> actually arrives.
	     */
	    Future<String> result = futureResult.map(
		    new Mapper<Boolean, String>() {
			public String apply(Boolean obj) {
			    if (Boolean.TRUE == obj)
				return "Person Saved";
			    else
				return "Person not saved";
			}
		    }, getContext().dispatcher());

	    /* Pipe above calculated result to the caller. */
	    Patterns.pipe(result, getContext().dispatcher()).pipeTo(
		    getSender(), getSelf());

	} else if (arg0 instanceof Integer) {
	    /*
	     * This call to repository will run in different thread: so that we
	     * don't perform blocking operations in actors.
	     */
	    Future<List<Person>> futureResult = Futures.future(
		    () -> {
			List<Person> persons = (List<Person>) personRepository
				.findAll();
			return persons;
		    }, getContext().dispatcher());

	    /* Pipe above calculated result to the caller. */
	    Patterns.pipe(futureResult, getContext().dispatcher()).pipeTo(
		    getSender(), getSelf());
	}

    }
}

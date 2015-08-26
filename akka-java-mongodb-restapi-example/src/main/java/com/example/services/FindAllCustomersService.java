package com.example.services;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import scala.concurrent.Future;
import akka.actor.UntypedActor;
import akka.dispatch.Futures;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.pattern.Patterns;

import com.eaxmple.model.Customer;
import com.eaxmple.repositories.CustomerRepository;
import com.example.services.request.FindAllCustomersRequest;
import com.example.vo.CustomerVO;

@Named("FindAllCustomersService")
@Scope("prototype")
public class FindAllCustomersService extends UntypedActor {

    final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private CustomerRepository customerRepository;

    @Inject
    public FindAllCustomersService(
	    @Named("customerRepository") CustomerRepository customerRepository) {
	this.customerRepository = customerRepository;
    }

    @Override
    public void onReceive(Object message) throws Exception {
	log.debug(getSelf().path() + " received message:: " + message);
	if (message instanceof FindAllCustomersRequest) {
	    final FindAllCustomersRequest request = (FindAllCustomersRequest) message;
	    log.debug(getSelf().path() + ":: processing :: " + request);
	    final Future<List<CustomerVO>> response = findCustomers();
	    Patterns.pipe(response, getContext().dispatcher()).pipeTo(
		    getSender(), getSelf());
	} else {
	    unhandled(message);
	}
    }

    private Future<List<CustomerVO>> findCustomers() {
	return Futures.future(() -> {
	    // Thread.sleep(100000);
		final Iterable<Customer> customers = customerRepository
			.findAll();
		final List<CustomerVO> response = new ArrayList<>();
		for (Customer cus : customers) {
		    final CustomerVO vo = new CustomerVO();
		    vo.setId(cus.getId());
		    vo.setFirstName(cus.getFirstName());
		    vo.setLastName(cus.getLastName());
		    vo.setEmail(cus.getEmail());
		    vo.setContactNumber(cus.getContactNumber());
		    response.add(vo);
		}
		return response;
	    }, getContext().dispatcher());
    }

}

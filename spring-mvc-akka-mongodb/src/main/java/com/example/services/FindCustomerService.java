package com.example.services;

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
import com.example.services.request.FindCustomerRequest;
import com.example.vo.CustomerVO;

@Named("FindCustomerService")
@Scope("prototype")
public class FindCustomerService extends UntypedActor {

    final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private CustomerRepository customerRepository;

    @Inject
    public FindCustomerService(
	    @Named("customerRepository") CustomerRepository customerRepository) {
	this.customerRepository = customerRepository;
    }

    @Override
    public void onReceive(Object message) throws Exception {
	log.debug(getSelf().path() + " received message:: " + message);
	if (message instanceof FindCustomerRequest) {
	    final FindCustomerRequest request = (FindCustomerRequest) message;
	    log.debug(getSelf().path() + ":: processing :: " + request);
	    final Future<CustomerVO> response = findCustomerById(request
		    .getId());
	    Patterns.pipe(response, getContext().dispatcher()).pipeTo(
		    getSender(), getSelf());
	} else {
	    unhandled(message);
	}
    }

    private Future<CustomerVO> findCustomerById(final String id) {
	return Futures.future(() -> {
	    //Thread.sleep(100000);
	    final Customer customer = customerRepository.findOne(id);
	    final CustomerVO customerVO = new CustomerVO();
	    customerVO.setId(id);
	    if (null != customer) {
		customerVO.setFirstName(customer.getFirstName());
		customerVO.setLastName(customer.getLastName());
		customerVO.setEmail(customer.getEmail());
		customerVO.setContactNumber(customer.getContactNumber());
	    }
	    return customerVO;
	}, getContext().dispatcher());
    }

}

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
import com.example.services.request.CreateNewCustomerRequest;
import com.example.vo.CustomerVO;

@Named("CreateNewCustomerService")
@Scope("prototype")
public class CreateNewCustomerService extends UntypedActor {

    final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private CustomerRepository customerRepository;

    @Inject
    public CreateNewCustomerService(
	    @Named("customerRepository") CustomerRepository customerRepository) {
	this.customerRepository = customerRepository;
    }

    @Override
    public void onReceive(Object message) throws Exception {
	log.debug(getSelf().path() + " received message:: " + message);
	if (message instanceof CreateNewCustomerRequest) {
	    final CreateNewCustomerRequest request = (CreateNewCustomerRequest) message;
	    log.debug(getSelf().path() + ":: processing :: " + request);
	    final Future<Boolean> response = createCustomer(request
		    .getCustomer());
	    Patterns.pipe(response, getContext().dispatcher()).pipeTo(
		    getSender(), getSelf());
	} else {
	    unhandled(message);
	}
    }

    private Future<Boolean> createCustomer(final CustomerVO customerVO) {
	return Futures.future(() -> {
	    // Thread.sleep(100000);
		final Customer cus = new Customer();
		cus.setFirstName(customerVO.getFirstName());
		cus.setLastName(customerVO.getLastName());
		cus.setEmail(customerVO.getEmail());
		cus.setContactNumber(customerVO.getContactNumber());
		final Customer repositoryResp = customerRepository.save(cus);
		final Boolean result = repositoryResp == null ? Boolean.FALSE : Boolean.TRUE;
		return result;
	    }, getContext().dispatcher());
    }

}

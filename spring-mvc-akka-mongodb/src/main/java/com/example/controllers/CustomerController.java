package com.example.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.dispatch.Futures;
import akka.dispatch.OnFailure;
import akka.dispatch.OnSuccess;
import akka.pattern.AskTimeoutException;
import akka.pattern.Patterns;

import com.example.services.request.CreateNewCustomerRequest;
import com.example.services.request.FindAllCustomersRequest;
import com.example.services.request.FindCustomerRequest;
import com.example.vo.CustomerVO;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Inject
    @Qualifier("findCustomerService")
    private ActorRef findCustomerService;

    @Inject
    @Qualifier("findAllCustomersService")
    private ActorRef findAllCustomersService;

    @Inject
    @Qualifier("createNewCustomerService")
    private ActorRef createNewCustomerService;

    @Inject
    private ActorSystem actorSystem;

    private final FiniteDuration timeOutDuration = Duration.create(150,
	    TimeUnit.MILLISECONDS);

    private final Long askTimeOut = 100l;

    @RequestMapping("/{customerId}")
    public DeferredResult<CustomerVO> getCustomer(
	    @PathVariable("customerId") final String customerId) {

	final DeferredResult<CustomerVO> deferredResult = new DeferredResult<>();

	final FindCustomerRequest request = new FindCustomerRequest();
	request.setId(customerId);

	final Future<Object> serviceResponse = Patterns.ask(
		findCustomerService, request, askTimeOut);

	final Future<Object> failedResponse = Futures
		.failed(new AskTimeoutException(
			"Timeout occured while processing the request."));

	final Future<Object> finalResponse = Patterns.after(timeOutDuration,
		actorSystem.scheduler(), actorSystem.dispatcher(),
		failedResponse);

	final Future<Object> result = Futures.firstCompletedOf(
		Arrays.<Future<Object>> asList(serviceResponse, finalResponse),
		actorSystem.dispatcher());

	result.onSuccess(new OnSuccess<Object>() {

	    @Override
	    public void onSuccess(Object arg0) throws Throwable {
		final CustomerVO customer = (CustomerVO) arg0;
		deferredResult.setResult(customer);
	    }
	}, actorSystem.dispatcher());

	result.onFailure(new OnFailure() {

	    @Override
	    public void onFailure(Throwable arg0) throws Throwable {
		deferredResult.setErrorResult(arg0);
	    }
	}, actorSystem.dispatcher());

	return deferredResult;
    }

    @RequestMapping(method = RequestMethod.GET)
    public DeferredResult<List<CustomerVO>> getCustomers() {

	final DeferredResult<List<CustomerVO>> deferredResult = new DeferredResult<>();
	final FindAllCustomersRequest request = new FindAllCustomersRequest();

	final Future<Object> serviceResponse = Patterns.ask(
		findAllCustomersService, request, askTimeOut);

	final Future<Object> failedResponse = Futures
		.failed(new AskTimeoutException(
			"Timeout occured while processing the request."));

	final Future<Object> finalResponse = Patterns.after(timeOutDuration,
		actorSystem.scheduler(), actorSystem.dispatcher(),
		failedResponse);

	final Future<Object> result = Futures.firstCompletedOf(
		Arrays.<Future<Object>> asList(serviceResponse, finalResponse),
		actorSystem.dispatcher());

	result.onSuccess(new OnSuccess<Object>() {

	    @Override
	    public void onSuccess(Object arg0) throws Throwable {
		final List<CustomerVO> customer = (List<CustomerVO>) arg0;
		deferredResult.setResult(customer);
	    }
	}, actorSystem.dispatcher());

	result.onFailure(new OnFailure() {

	    @Override
	    public void onFailure(Throwable arg0) throws Throwable {
		deferredResult.setErrorResult(arg0);
	    }
	}, actorSystem.dispatcher());

	return deferredResult;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public DeferredResult<Boolean> createNewCustomer(
	    final @RequestBody CustomerVO customer) {

	final DeferredResult<Boolean> deferredResult = new DeferredResult<>();
	final CreateNewCustomerRequest request = new CreateNewCustomerRequest();
	request.setCustomer(customer);

	final Future<Object> serviceResponse = Patterns.ask(
		createNewCustomerService, request, askTimeOut);

	final Future<Object> failedResponse = Futures
		.failed(new AskTimeoutException(
			"Timeout occured while processing the request."));

	final Future<Object> finalResponse = Patterns.after(timeOutDuration,
		actorSystem.scheduler(), actorSystem.dispatcher(),
		failedResponse);

	final Future<Object> result = Futures.firstCompletedOf(
		Arrays.<Future<Object>> asList(serviceResponse, finalResponse),
		actorSystem.dispatcher());

	result.onSuccess(new OnSuccess<Object>() {

	    @Override
	    public void onSuccess(Object arg0) throws Throwable {
		final Boolean created = (Boolean) arg0;
		deferredResult.setResult(created);
	    }
	}, actorSystem.dispatcher());

	result.onFailure(new OnFailure() {

	    @Override
	    public void onFailure(Throwable arg0) throws Throwable {
		deferredResult.setErrorResult(arg0);
	    }
	}, actorSystem.dispatcher());

	return deferredResult;
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Internal Server Error.")
    @ExceptionHandler(value = { Exception.class })
    public void handleException() {
    }

}

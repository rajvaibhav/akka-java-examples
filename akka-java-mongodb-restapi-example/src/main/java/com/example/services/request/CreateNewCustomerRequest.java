package com.example.services.request;

import java.io.Serializable;

import com.example.vo.CustomerVO;

public class CreateNewCustomerRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    private CustomerVO customer;

    public CreateNewCustomerRequest(CustomerVO customer) {
	super();
	this.customer = customer;
    }

    public CreateNewCustomerRequest() {
	super();
	// TODO Auto-generated constructor stub
    }

    public CustomerVO getCustomer() {
	return customer;
    }

    public void setCustomer(CustomerVO customer) {
	this.customer = customer;
    }

    @Override
    public String toString() {
	return "CreateNewCustomerRequest [customer=" + customer + "]";
    }

}

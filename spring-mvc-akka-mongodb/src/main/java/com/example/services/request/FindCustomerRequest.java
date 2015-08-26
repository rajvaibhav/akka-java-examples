package com.example.services.request;

import java.io.Serializable;

public class FindCustomerRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

}

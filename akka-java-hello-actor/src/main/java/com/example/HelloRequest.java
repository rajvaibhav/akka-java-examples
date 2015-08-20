package com.example;

import java.io.Serializable;

public final class HelloRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    public final String message;

    public HelloRequest(String message) {
	super();
	this.message = message;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((message == null) ? 0 : message.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	HelloRequest other = (HelloRequest) obj;
	if (message == null) {
	    if (other.message != null)
		return false;
	} else if (!message.equals(other.message))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "HelloRequest [message=" + message + "]";
    }

}

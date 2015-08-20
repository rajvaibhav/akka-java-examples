package com.example;

import java.io.Serializable;

public final class HelloResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    public final String respMessage;

    public HelloResponse(String respMessage) {
	super();
	this.respMessage = respMessage;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result
		+ ((respMessage == null) ? 0 : respMessage.hashCode());
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
	HelloResponse other = (HelloResponse) obj;
	if (respMessage == null) {
	    if (other.respMessage != null)
		return false;
	} else if (!respMessage.equals(other.respMessage))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "HelloResponse [respMessage=" + respMessage + "]";
    }

}

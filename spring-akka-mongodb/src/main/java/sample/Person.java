package sample;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "person")
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String _id;
    private String firstName;
    private String lastName;
    private Address address;

    public Person() {
	super();
	// TODO Auto-generated constructor stub
    }

    public Person(String firstName, String lastName, Address address) {
	super();
	this.firstName = firstName;
	this.lastName = lastName;
	this.address = address;
    }

    public String get_id() {
	return _id;
    }

    public void set_id(String _id) {
	this._id = _id;
    }

    public String getFirstName() {
	return firstName;
    }

    public void setFirstName(String firstName) {
	this.firstName = firstName;
    }

    public String getLastName() {
	return lastName;
    }

    public void setLastName(String lastName) {
	this.lastName = lastName;
    }

    public Address getAddress() {
	return address;
    }

    public void setAddress(Address address) {
	this.address = address;
    }

    @Override
    public String toString() {
	return "Person [_id=" + _id + ", firstName=" + firstName
		+ ", lastName=" + lastName + ", address=" + address + "]";
    }

}

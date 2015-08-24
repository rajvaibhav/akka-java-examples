package sample;

import java.io.Serializable;

public class Address implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer houseNo;
    private String street;
    private String location;
    private String city;

    public Integer getHouseNo() {
	return houseNo;
    }

    public void setHouseNo(Integer houseNo) {
	this.houseNo = houseNo;
    }

    public String getStreet() {
	return street;
    }

    public void setStreet(String street) {
	this.street = street;
    }

    public String getLocation() {
	return location;
    }

    public void setLocation(String location) {
	this.location = location;
    }

    public String getCity() {
	return city;
    }

    public void setCity(String city) {
	this.city = city;
    }

    public Address(Integer houseNo, String street, String location, String city) {
	super();
	this.houseNo = houseNo;
	this.street = street;
	this.location = location;
	this.city = city;
    }

    public Address() {
	super();
	// TODO Auto-generated constructor stub
    }

    @Override
    public String toString() {
	return "Address [houseNo=" + houseNo + ", street=" + street
		+ ", location=" + location + ", city=" + city + "]";
    }

}

package com.example.demo;

public class Company {
	String name;
	String telephone;
	String email;
	String postalCode;
	String street;
	String locality;
	String country;

	@Override
	public String toString() {
		return "Company{" + "name='" + name + '\'' + ", telephone='" + telephone + '\'' + ", email='" + email + '\'' + ", postalCode='" + postalCode + '\'' + ", street='" + street + '\'' + ", locality='" + locality + '\'' + ", country='" + country + '\'' + '}';
	}
}

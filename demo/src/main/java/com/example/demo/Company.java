package com.example.demo;


public class Company {
	String url;
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

	public String getName() {
		return name;
	}

	public String getTelephone() {
		return telephone;
	}

	public String getEmail() {
		return email;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public String getStreet() {
		return street;
	}

	public String getLocality() {
		return locality;
	}

	public String getCountry() {
		return country;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}

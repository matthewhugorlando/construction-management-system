package com.ironyard.data;

import javax.persistence.*;

/**
 * Created by matthewhug on 2/27/17.
 */
@Entity
@Table(name = "CAddress")
public class CAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "caddress_sequence")
    @SequenceGenerator(name="caddress_sequence", sequenceName = "caddress_sequence")
    private long id;
    private String street;
    private String city;
    private String state;
    private String zipCode;

    public CAddress() {
    }

    public CAddress(String street, String city, String state, String zipCode) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}

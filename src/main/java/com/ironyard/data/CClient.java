package com.ironyard.data;

import javax.persistence.*;

/**
 * Created by matthewhug on 3/13/17.
 */
@Entity
@Table(name = "CClient")
public class CClient {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cclient_sequence")
    @SequenceGenerator(name="cclient_sequence", sequenceName = "cclient_sequence")
    private long id;
    private String name;
    private String contactFirstName;
    private String contactLastName;
    private String contactPhoneNumber;
    private String contactEmail;

    @ManyToOne
    private CAddress address;

    public CClient() {
    }

    public CClient(String name, String contactFirstName, String contactLastName, String contactPhoneNumber, String contactEmail) {
        this.name = name;
        this.contactFirstName = contactFirstName;
        this.contactLastName = contactLastName;
        this.contactPhoneNumber = contactPhoneNumber;
        this.contactEmail = contactEmail;
    }

    public CClient(String name, String contactFirstName, String contactLastName, String contactPhoneNumber, String contactEmail, CAddress address) {
        this.name = name;
        this.contactFirstName = contactFirstName;
        this.contactLastName = contactLastName;
        this.contactPhoneNumber = contactPhoneNumber;
        this.contactEmail = contactEmail;
        this.address = address;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactFirstName() {
        return contactFirstName;
    }

    public void setContactFirstName(String contactFirstName) {
        this.contactFirstName = contactFirstName;
    }

    public String getContactLastName() {
        return contactLastName;
    }

    public void setContactLastName(String contactLastName) {
        this.contactLastName = contactLastName;
    }

    public String getContactPhoneNumber() {
        return contactPhoneNumber;
    }

    public void setContactPhoneNumber(String contactPhoneNumber) {
        this.contactPhoneNumber = contactPhoneNumber;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public CAddress getAddress() {
        return address;
    }

    public void setAddress(CAddress address) {
        this.address = address;
    }
}

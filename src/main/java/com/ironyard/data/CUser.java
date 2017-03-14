package com.ironyard.data;

import javax.persistence.*;
import java.util.List;

/**
 * Created by matthewhug on 3/8/17.
 */
@Entity
@Table(name = "CUser")
public class CUser {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cuser_sequence")
    @SequenceGenerator(name="cuser_sequence", sequenceName = "cuser_sequence")
    private long id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String permissionLevel;
    private boolean active;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private CAddress currentAddress;

//    @ManyToMany
//    private List<CJob> jobs;
//    @ManyToMany
//    private List<CTask> tasks;
//    @ManyToMany
//    private List<CVehicle> vehicles;
//    @ManyToMany
//    private List<CAddress> pastAddresses;


    public CUser() {
    }

    public CUser(String firstName, String lastName, String username, String password, String email, String phoneNumber, String permissionLevel, boolean active) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.permissionLevel = permissionLevel;
        this.active = active;
    }

    public CUser(String firstName, String lastName, String username, String password, String email, String phoneNumber, String permissionLevel, boolean active, CAddress currentAddress) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.permissionLevel = permissionLevel;
        this.active = active;
        this.currentAddress = currentAddress;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPermissionLevel() {
        return permissionLevel;
    }

    public void setPermissionLevel(String permissionLevel) {
        this.permissionLevel = permissionLevel;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public CAddress getCurrentAddress() {
        return currentAddress;
    }

    public void setCurrentAddress(CAddress currentAddress) {
        this.currentAddress = currentAddress;
    }
}

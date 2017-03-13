package com.ironyard.data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by matthewhug on 2/27/17.
 */
//@Entity
//@Table(name = "CVehicle", schema = "CMS")
//@PrimaryKeyJoinColumn(name="id", referencedColumnName="id")
public class CVehicle extends InvHolder{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cvehicle_sequence")
    @SequenceGenerator(name="cvehicle_sequence", sequenceName = "cvehicle_sequence", schema = "CMS")
    private long id;
    private String make;
    private String model;
    private int year;
    private long mileage;
    private Date lastMaintenance;
    private String status;

    @ManyToMany
    private List<CUser> currentUsers;

    @ManyToMany
    private List<CUser> pastUsers;

    public CVehicle() {
    }

    public CVehicle(String name, String make, String model, int year) {
        this.setName(name);
        this.make = make;
        this.model = model;
        this.year = year;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}

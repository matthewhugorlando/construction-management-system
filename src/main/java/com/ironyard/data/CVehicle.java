package com.ironyard.data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by matthewhug on 2/27/17.
 */
@Entity
@Table(name = "CVehicle", schema = "CMS")
@PrimaryKeyJoinColumn(name="id", referencedColumnName="id")
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

    @ManyToOne
    private CUser currentOwner;

    public CVehicle() {
    }

    public CVehicle(String make, String model, int year, long mileage, Date lastMaintenance, String status, CUser currentOwner) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.mileage = mileage;
        this.lastMaintenance = lastMaintenance;
        this.status = status;
        this.currentOwner = currentOwner;
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

    public long getMileage() {
        return mileage;
    }

    public void setMileage(long mileage) {
        this.mileage = mileage;
    }

    public Date getLastMaintenance() {
        return lastMaintenance;
    }

    public void setLastMaintenance(Date lastMaintenance) {
        this.lastMaintenance = lastMaintenance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CUser getCurrentOwner() {
        return currentOwner;
    }

    public void setCurrentOwner(CUser currentOwner) {
        this.currentOwner = currentOwner;
    }
}

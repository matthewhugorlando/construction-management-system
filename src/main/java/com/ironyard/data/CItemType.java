package com.ironyard.data;

import javax.persistence.*;

/**
 * Created by matthewhug on 3/10/17.
 */
@Entity
@Table(name = "CItemType", schema = "CMS")
public class CItemType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "citemtype_sequence")
    @SequenceGenerator(name="citemtype_sequence", sequenceName = "citemtype_sequence", schema = "CMS")
    private long id;
    private String name;
    private boolean active;
    private String unitOfMeasurement;

    @ManyToOne
    private CUser createdBy;

    public CItemType() {
    }

    public CItemType(String name, boolean status, String unitOfMeasurement, CUser createdBy) {
        this.name = name;
        this.active = status;
        this.unitOfMeasurement = unitOfMeasurement;
        this.createdBy = createdBy;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getUnitOfMeasurement() {
        return unitOfMeasurement;
    }

    public void setUnitOfMeasurement(String unitOfMeasurement) {
        this.unitOfMeasurement = unitOfMeasurement;
    }

    public CUser getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(CUser createdBy) {
        this.createdBy = createdBy;
    }
}

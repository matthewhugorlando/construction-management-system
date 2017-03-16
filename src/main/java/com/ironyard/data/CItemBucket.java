package com.ironyard.data;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.List;

/**
 * Created by matthewhug on 3/14/17.
 */
@Entity
@Table(name = "CItemBucket")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class CItemBucket {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "citembucket_sequence")
    @SequenceGenerator(name="citembucket_sequence", sequenceName = "citembucket_sequence")
    private long id;
    private int quantity;
    private String status;
    private Double totalCost;

    @ManyToOne
    private CItemType bucketType;

    @OneToMany
    private List<CItem> items;

    @ManyToOne
    @JsonIdentityReference(alwaysAsId=true)
    private InvHolder location;

    public CItemBucket() {
    }

    public CItemBucket(int quantity, String status, CItemType bucketType, InvHolder location) {
        this.quantity = quantity;
        this.status = status;
        this.bucketType = bucketType;
        this.location = location;
    }

    public CItemBucket(int quantity, String status, Double totalCost, CItemType bucketType, List<CItem> items, InvHolder location) {
        this.quantity = quantity;
        this.status = status;
        this.totalCost = totalCost;
        this.bucketType = bucketType;
        this.items = items;
        this.location = location;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public CItemType getBucketType() {
        return bucketType;
    }

    public void setBucketType(CItemType bucketType) {
        this.bucketType = bucketType;
    }

    public List<CItem> getItems() {
        return items;
    }

    public void setItems(List<CItem> items) {
        this.items = items;
    }

    public InvHolder getLocation() {
        return location;
    }

    public void setLocation(InvHolder location) {
        this.location = location;
    }
}

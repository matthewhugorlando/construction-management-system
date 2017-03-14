package com.ironyard.data;

import javax.persistence.*;
import java.util.List;

/**
 * Created by matthewhug on 3/14/17.
 */
@Entity
@Table(name = "CItemBucket", schema = "CMS")
public class CItemBucket {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "citembucket_sequence")
    @SequenceGenerator(name="citembucket_sequence", sequenceName = "citembucket_sequence", schema = "CMS")
    private long id;
    private int quantity;
    private String status;

    @ManyToOne
    private CItemType bucketType;

    @OneToMany
    private List<CItem> items;

    @ManyToOne
    private InvHolder location;

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

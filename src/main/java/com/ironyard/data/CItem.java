package com.ironyard.data;

import javax.persistence.*;

/**
 * Created by matthewhug on 3/9/17.
 */
@Entity
@Table(name = "CItem", schema = "CMS")
public class CItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "citem_sequence")
    @SequenceGenerator(name="citem_sequence", sequenceName = "citem_sequence", schema = "CMS")
    private long id;
    private int quantity;
    private String status;

    @ManyToOne
    private CItemType itemType;

    public CItem() {
    }

    public CItem(int quantity, String status, CItemType itemType) {
        this.quantity = quantity;
        this.status = status;
        this.itemType = itemType;
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

    public CItemType getItemType() {
        return itemType;
    }

    public void setItemType(CItemType itemType) {
        this.itemType = itemType;
    }
}

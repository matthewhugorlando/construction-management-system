package com.ironyard.data;

import javax.persistence.*;

/**
 * Created by matthewhug on 3/9/17.
 */
@Entity
@Table(name = "CItem")
public class CItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "citem_sequence")
    @SequenceGenerator(name="citem_sequence", sequenceName = "citem_sequence")
    private long id;
    private String status;

    @ManyToOne
    private CItemType itemType;

    public CItem() {
    }

    public CItem(String status, CItemType itemType) {
        this.status = status;
        this.itemType = itemType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

package com.ironyard.data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by matthewhug on 3/13/17.
 */
@Entity
@Table(name = "CDelivery", schema = "CMS")
@PrimaryKeyJoinColumn(name="id", referencedColumnName="id")
public class CDelivery extends InvHolder {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cjob_sequence")
    @SequenceGenerator(name="cjob_sequence", sequenceName = "cjob_sequence", schema = "CMS")
    private long id;
    private Date deliveryDate;
    private String deliveredBy;

    @ManyToOne
    private CUser receivedBy;

    @ManyToMany
    private List<CItem> cItems;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getDeliveredBy() {
        return deliveredBy;
    }

    public void setDeliveredBy(String deliveredBy) {
        this.deliveredBy = deliveredBy;
    }

    public CUser getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(CUser receivedBy) {
        this.receivedBy = receivedBy;
    }

    public List<CItem> getcItems() {
        return cItems;
    }

    public void setcItems(List<CItem> cItems) {
        this.cItems = cItems;
    }
}

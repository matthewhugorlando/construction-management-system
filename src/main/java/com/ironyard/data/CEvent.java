package com.ironyard.data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by matthewhug on 3/10/17.
 */
@Entity
@Table(name = "CEvent")
@PrimaryKeyJoinColumn(name="id", referencedColumnName="id")
public class CEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cevent_sequence")
    @SequenceGenerator(name = "cevent_sequence", sequenceName = "cevent_sequence")
    private long id;
    private Date timeStamp;
    private String eventAction;

    @ManyToOne
    private CUser createdBy;

    @ManyToOne
    private CUser cUserEvent;

    @ManyToOne
    private CItemType cItemTypeEvent;

    @ManyToOne
    private CItem cItemEvent;

    @ManyToOne
    private CWarehouse cWarehouseEvent;

    @ManyToOne
    private CJob cJobEvent;

    public CEvent() {
    }

    public CEvent(Date timeStamp, String eventAction, CUser createdBy, CUser cUserEvent) {
        this.timeStamp = timeStamp;
        this.eventAction = eventAction;
        this.createdBy = createdBy;
        this.cUserEvent = cUserEvent;
    }

    public CEvent(Date timeStamp, String eventAction, CUser createdBy, CItemType cItemTypeEvent) {
        this.timeStamp = timeStamp;
        this.eventAction = eventAction;
        this.createdBy = createdBy;
        this.cItemTypeEvent = cItemTypeEvent;
    }

    public CEvent(Date timeStamp, String eventAction, CUser createdBy, CItem cItemEvent) {
        this.timeStamp = timeStamp;
        this.eventAction = eventAction;
        this.createdBy = createdBy;
        this.cItemEvent = cItemEvent;
    }

    public CEvent(Date timeStamp, String eventAction, CUser createdBy, CWarehouse cWarehouseEvent) {
        this.timeStamp = timeStamp;
        this.eventAction = eventAction;
        this.createdBy = createdBy;
        this.cWarehouseEvent = cWarehouseEvent;
    }

    public CEvent(Date timeStamp, String eventAction, CUser createdBy, CJob cJobEvent) {
        this.timeStamp = timeStamp;
        this.eventAction = eventAction;
        this.createdBy = createdBy;
        this.cJobEvent = cJobEvent;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getEventAction() {
        return eventAction;
    }

    public void setEventAction(String eventAction) {
        this.eventAction = eventAction;
    }

    public CUser getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(CUser createdBy) {
        this.createdBy = createdBy;
    }

    public CUser getcUserEvent() {
        return cUserEvent;
    }

    public void setcUserEvent(CUser cUserEvent) {
        this.cUserEvent = cUserEvent;
    }

    public CItemType getcItemTypeEvent() {
        return cItemTypeEvent;
    }

    public void setcItemTypeEvent(CItemType cItemTypeEvent) {
        this.cItemTypeEvent = cItemTypeEvent;
    }

    public CItem getcItemEvent() {
        return cItemEvent;
    }

    public void setcItemEvent(CItem cItemEvent) {
        this.cItemEvent = cItemEvent;
    }

    public CWarehouse getcWarehouseEvent() {
        return cWarehouseEvent;
    }

    public void setcWarehouseEvent(CWarehouse cWarehouseEvent) {
        this.cWarehouseEvent = cWarehouseEvent;
    }
}


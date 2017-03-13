package com.ironyard.data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by matthewhug on 3/10/17.
 */
@Entity
@Table(name = "CEvent", schema = "CMS")
@PrimaryKeyJoinColumn(name="id", referencedColumnName="id")
public class CEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cevent_sequence")
    @SequenceGenerator(name = "cevent_sequence", sequenceName = "cevent_sequence", schema = "CMS")
    private long id;
    private Date timeStamp;
    private String eventAction;

    @ManyToOne
    private CUser createdBy;

    @ManyToOne
    private CUser cUserEvent;

    @ManyToOne
    private CItemType cItemTypeEvent;

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
}

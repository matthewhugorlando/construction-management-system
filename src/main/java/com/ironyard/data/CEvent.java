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
    @SequenceGenerator(name="cevent_sequence", sequenceName = "cevent_sequence", schema = "CMS")
    private long id;
    private Date timeStamp;
    private String objType;
    private long objId;
    private String eventAction;

    public CEvent() {
    }

    public CEvent(Date timeStamp, String objType, long objId, String eventAction) {
        this.timeStamp = timeStamp;
        this.objType = objType;
        this.objId = objId;
        this.eventAction = eventAction;
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

    public String getObjType() {
        return objType;
    }

    public void setObjType(String objType) {
        this.objType = objType;
    }

    public long getObjId() {
        return objId;
    }

    public void setObjId(long objId) {
        this.objId = objId;
    }

    public String getEventAction() {
        return eventAction;
    }

    public void setEventAction(String eventAction) {
        this.eventAction = eventAction;
    }
}

package com.ironyard.data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by matthewhug on 3/8/17.
 */
@Entity
@Table(name = "CTask")
public class CTask {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ctask_sequence")
    @SequenceGenerator(name="ctask_sequence", sequenceName = "ctask_sequence")
    private long id;
    private String name;
    @Column(length = 1000)
    private String body;
    private Date postDate;
    private Date completedDate;
    private boolean completed;

    @ManyToOne
    private CUser createdBy;

    @ManyToOne
    private CUser completedBy;

    @ManyToOne
    private CJob job;

    public CTask() {
    }

    public CTask(String name, String body, Date postDate, boolean completed, CUser createdBy, CJob job) {
        this.name = name;
        this.body = body;
        this.postDate = postDate;
        this.completed = completed;
        this.createdBy = createdBy;
        this.job = job;
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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public Date getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(Date completedDate) {
        this.completedDate = completedDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public CUser getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(CUser createdBy) {
        this.createdBy = createdBy;
    }

    public CUser getCompletedBy() {
        return completedBy;
    }

    public void setCompletedBy(CUser completedBy) {
        this.completedBy = completedBy;
    }

    public CJob getJob() {
        return job;
    }

    public void setJob(CJob job) {
        this.job = job;
    }
}
//
//@Id
//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "c_sequence")
//@SequenceGenerator(name="c_sequence", sequenceName = "c_sequence", schema = "CMS")
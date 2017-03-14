package com.ironyard.data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by matthewhug on 3/8/17.
 */
@Entity
@Table(name = "CJob")
@PrimaryKeyJoinColumn(name="id", referencedColumnName="id")
public class CJob extends InvHolder{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cjob_sequence")
    @SequenceGenerator(name="cjob_sequence", sequenceName = "cjob_sequence")
    private long id;
    private Date startDate;
    private Date endDate;
    private String status;
    private double jobPrice;

    @ManyToOne
    private CUser createdBy;

    @ManyToMany
    private List<CUser> workedOnBy;

    @ManyToOne
    private CUser markedAsComplete;

    @ManyToOne
    private CClient client;

    @OneToMany
    List<CTask> tasks;

    public CJob() {
    }

    public CJob(Date start, String status, double jobPrice, CUser createdBy, List<CUser> workedOnBy, CClient client) {
        this.startDate = start;
        this.status = status;
        this.jobPrice = jobPrice;
        this.createdBy = createdBy;
        this.workedOnBy = workedOnBy;
        this.client = client;
    }

    public CJob(Date start, Date end, String status, double jobPrice, CUser createdBy, List<CUser> workedOnBy, CUser markedAsComplete, CClient client) {
        this.startDate = start;
        this.endDate = end;
        this.status = status;
        this.jobPrice = jobPrice;
        this.createdBy = createdBy;
        this.workedOnBy = workedOnBy;
        this.markedAsComplete = markedAsComplete;
        this.client = client;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getJobPrice() {
        return jobPrice;
    }

    public void setJobPrice(double jobPrice) {
        this.jobPrice = jobPrice;
    }

    public CUser getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(CUser createdBy) {
        this.createdBy = createdBy;
    }

    public List<CUser> getWorkedOnBy() {
        return workedOnBy;
    }

    public void setWorkedOnBy(List<CUser> workedOnBy) {
        this.workedOnBy = workedOnBy;
    }

    public CUser getMarkedAsComplete() {
        return markedAsComplete;
    }

    public void setMarkedAsComplete(CUser markedAsComplete) {
        this.markedAsComplete = markedAsComplete;
    }

    public CClient getClient() {
        return client;
    }

    public void setClient(CClient client) {
        this.client = client;
    }

    public List<CTask> getTasks() {
        return tasks;
    }

    public void setTasks(List<CTask> tasks) {
        this.tasks = tasks;
    }
}

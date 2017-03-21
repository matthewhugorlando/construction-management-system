package com.ironyard.dto;

/**
 * Created by matthewhug on 3/21/17.
 */
public class CTaskDTO {
    private String name;
    private String body;
    private long jobId;

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

    public long getJobId() {
        return jobId;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
    }
}

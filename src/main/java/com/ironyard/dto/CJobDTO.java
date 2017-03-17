package com.ironyard.dto;

import com.ironyard.data.CAddress;

import java.util.List;

/**
 * Created by matthewhug on 3/17/17.
 */
public class CJobDTO {
    private String name;
    private long clId;
    private String startDate;
    private String status;
    private double jobPrice;
    private CAddress address;
    private List<InventoryDTO> inventory;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getClId() {
        return clId;
    }

    public void setClId(long clId) {
        this.clId = clId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
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

    public CAddress getAddress() {
        return address;
    }

    public void setAddress(CAddress address) {
        this.address = address;
    }

    public List<InventoryDTO> getInventory() {
        return inventory;
    }

    public void setInventory(List<InventoryDTO> inventory) {
        this.inventory = inventory;
    }
}

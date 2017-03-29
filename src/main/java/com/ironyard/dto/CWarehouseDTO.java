package com.ironyard.dto;

import com.ironyard.data.CAddress;

import java.util.List;

/**
 * Created by matthewhug on 3/29/17.
 */
public class CWarehouseDTO {
    private String name;
    private boolean active;
    private CAddress address;
    private List<InventoryDTO> inventory;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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

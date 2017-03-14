package com.ironyard.data;

import javax.persistence.*;

/**
 * Created by matthewhug on 2/27/17.
 */
@Entity
@Table(name = "CWarehouse")
@PrimaryKeyJoinColumn(name="id", referencedColumnName="id")
public class CWarehouse extends InvHolder {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cwarehouse_sequence")
    @SequenceGenerator(name="cwarehouse_sequence", sequenceName = "cwarehouse_sequence")
    private long id;
    private boolean active;


    public CWarehouse() {
    }

    public CWarehouse(String name, boolean active) {
        this.setName(name);
        this.active = active;
    }

    public CWarehouse(String name, CAddress location, boolean active) {
        this.setName(name);
        this.setLocation(location);
        this.active = active;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}

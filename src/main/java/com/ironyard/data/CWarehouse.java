package com.ironyard.data;

import javax.persistence.*;

/**
 * Created by matthewhug on 2/27/17.
 */
//@Entity
//@Table(name = "CWarehouse", schema = "CMS")
//@PrimaryKeyJoinColumn(name="id", referencedColumnName="id")
public class CWarehouse extends InvHolder {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cwarehouse_sequence")
    @SequenceGenerator(name="cwarehouse_sequence", sequenceName = "cwarehouse_sequence", schema = "CMS")
    private long id;
    private CAddress location;
    private String status;
    // Possibly add manager?


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CAddress getLocation() {
        return location;
    }

    public void setLocation(CAddress location) {
        this.location = location;
    }
}

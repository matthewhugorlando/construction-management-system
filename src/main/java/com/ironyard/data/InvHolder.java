package com.ironyard.data;

import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;

/**
 * Created by matthewhug on 2/27/17.
 */
//@Entity
//@Table(name = "InvHolder", schema = "CMS")
//@Inheritance(strategy=InheritanceType.JOINED)
public class InvHolder {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invholder_sequence")
    @SequenceGenerator(name="invholder_sequence", sequenceName = "invholder_sequence", schema = "CMS")
    private long id;
    private String name;

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
}

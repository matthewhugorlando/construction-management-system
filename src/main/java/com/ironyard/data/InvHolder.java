package com.ironyard.data;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;
import java.util.List;

/**
 * Created by matthewhug on 2/27/17.
 */
@Entity
@Table(name = "InvHolder")
@Inheritance(strategy=InheritanceType.JOINED)
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class InvHolder {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invholder_sequence")
    @SequenceGenerator(name="invholder_sequence", sequenceName = "invholder_sequence")
    private long id;
    @Column(unique=true)
    private String name;

    @ManyToOne
    private CAddress location;

    @OneToMany
    @JsonIdentityReference(alwaysAsId=true)
    private List<CItemBucket> inventory;

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

    public CAddress getLocation() {
        return location;
    }

    public void setLocation(CAddress location) {
        this.location = location;
    }

    public List<CItemBucket> getInventory() {
        return inventory;
    }

    public void setInventory(List<CItemBucket> inventory) {
        this.inventory = inventory;
    }
}

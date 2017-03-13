package com.ironyard.data;

import javax.persistence.*;

/**
 * Created by matthewhug on 3/9/17.
 */
//@Entity
//@Table(name = "CItem", schema = "CMS")
public class CItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "citem_sequence")
    @SequenceGenerator(name="citem_sequence", sequenceName = "citem_sequence", schema = "CMS")
    private long id;
    private int quantity;
    private String status;

    @ManyToOne
    private CItemType itemType;

}

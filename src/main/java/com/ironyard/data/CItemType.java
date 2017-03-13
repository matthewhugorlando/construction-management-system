package com.ironyard.data;

import javax.persistence.*;

/**
 * Created by matthewhug on 3/10/17.
 */
//@Entity
//@Table(name = "CItemType", schema = "CMS")
public class CItemType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "citemtype_sequence")
    @SequenceGenerator(name="citemtype_sequence", sequenceName = "citemtype_sequence", schema = "CMS")
    private long id;
    private String name;
    private String status;

}

package com.ironyard.data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by matthewhug on 3/8/17.
 */
//@Entity
//@Table(name = "CTask", schema = "CMS")
public class CTask {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ctask_sequence")
    @SequenceGenerator(name="ctask_sequence", sequenceName = "ctask_sequence", schema = "CMS")
    private long id;
    private String name;
    private String body;
    private Date postDate;
    private Date completedDate;
    private String status;

    @ManyToOne
    private CUser createdBy;

    @ManyToOne
    private CUser completedBy;

    @ManyToOne
    private CJob job;

}

//@Id
//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "c_sequence")
//@SequenceGenerator(name="c_sequence", sequenceName = "c_sequence", schema = "CMS")
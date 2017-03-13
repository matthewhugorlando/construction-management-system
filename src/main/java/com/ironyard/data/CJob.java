package com.ironyard.data;

import com.ironyard.dto.DSkyWeatherDTO;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by matthewhug on 3/8/17.
 */
//@Entity
//@Table(name = "CJob", schema = "CMS")
//@PrimaryKeyJoinColumn(name="id", referencedColumnName="id")
public class CJob {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cjob_sequence")
    @SequenceGenerator(name="cjob_sequence", sequenceName = "cjob_sequence", schema = "CMS")
    private long id;
    private CAddress location;
    private String client;
    private Date projectedStart;
    private Date actualStart;
    private Date projectedEnd;
    private Date actualEnd;
    private String status;

    @ManyToOne
    private CUser createdBy;

    @ManyToMany
    private CUser workedOnBy;

    @ManyToOne
    private CUser markedAsComplete;

    @ManyToOne
    private DSkyWeatherDTO currentWeather;

}

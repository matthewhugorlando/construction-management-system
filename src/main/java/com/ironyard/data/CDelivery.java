package com.ironyard.data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by matthewhug on 3/13/17.
 */
@Entity
@Table(name = "CDelivery", schema = "CMS")
@PrimaryKeyJoinColumn(name="id", referencedColumnName="id")
public class CDelivery extends InvHolder {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cjob_sequence")
    @SequenceGenerator(name="cjob_sequence", sequenceName = "cjob_sequence", schema = "CMS")
    private long id;
    private Date deliveryDate;
    private String deliveredBy;

    @ManyToOne
    private CUser receivedBy;

    @ManyToMany
    private List<CItem> cItems;

    

}

package com.ironyard.repo;

import com.ironyard.data.CEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by matthewhug on 3/13/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CEventRepoTest {
    @Autowired
    CEventRepo cEventRepo;

    @Test
    public void saveEvent(){
        Calendar c = Calendar.getInstance();
        Date now = c.getTime();

        CEvent ce = new CEvent(now, "Job", 1, "Created");

        cEventRepo.save(ce);

        CEvent ceCheck = cEventRepo.findOne(ce.getId());

        assertEquals("Database matches entered event", ce.getTimeStamp(), ceCheck.getTimeStamp());


    }
}
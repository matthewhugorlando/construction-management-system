package com.ironyard.repo;

import com.ironyard.data.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by matthewhug on 3/13/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CJobRepoTest {

    @Autowired
    CClientRepo cClientRepo;

    @Autowired
    CItemTypeRepo cItemTypeRepo;

    @Autowired
    CItemRepo cItemRepo;

    @Autowired
    CJobRepo cJobRepo;

    @Autowired
    CUserRepo cUserRepo;

    @Test
    @Transactional
    public void saveJob(){
        Calendar c = Calendar.getInstance();
        Date now = c.getTime();

        CAddress ca = new CAddress("123 Job Test Street", "Orlando", "FL", "32819");

        CUser cu1 = new CUser("TestFirstName", "TestLastName", "TestUserName", "TestPassword", "test@email.com", "123-456-7890", "BASIC", true, ca);
        cUserRepo.save(cu1);

        CUser cu2 = new CUser("Test2FirstName", "Test2LastName", "Test2UserName", "Test2Password", "test2@email.com", "123-456-0987", "BASIC", true, ca);
        cUserRepo.save(cu1);

        CItemType cit1 = new CItemType("Mastic", true, "Bucket", 10.00, cu1);
        cItemTypeRepo.save(cit1);

        CItem ci1 = new CItem(2, "PENDING", cit1);
        cItemRepo.save(ci1);

        CItemType cit2 = new CItemType("12-inch Flex", true, "Bag", 20.99, cu1);
        cItemTypeRepo.save(cit2);

        CItem ci2 = new CItem(4, "PENDING", cit2);
        cItemRepo.save(ci2);

        CClient cc = new CClient("Test Client", "ClientFN", "ClientLN", "4075678912", "client@email.com", ca);
        cClientRepo.save(cc);

        List<CUser> cus = new ArrayList<>();

        cus.add(cu1);
        cus.add(cu2);

        CJob cj = new CJob(now, "On Time", 2000.00, cu1, cus, cc);

        cJobRepo.save(cj);

        CJob cjCheck = cJobRepo.findOne(cj.getId());

        assertEquals("Database matches entered CJob start", cj.getStartDate(), cjCheck.getStartDate());

    }

}
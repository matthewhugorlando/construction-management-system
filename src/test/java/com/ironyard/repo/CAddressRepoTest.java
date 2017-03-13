package com.ironyard.repo;

import com.ironyard.data.CAddress;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by matthewhug on 3/13/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CAddressRepoTest {

    @Autowired
    CAddressRepo cAddressRepo;

    @Test
    public void saveAddress(){
        CAddress ca = new CAddress("123 Main Street", "Orlando", "FL", "32819");

        cAddressRepo.save(ca);

        CAddress caChecker = cAddressRepo.findOne(ca.getId());

        assertEquals("Database does not match saved Address street", ca.getStreet(), caChecker.getStreet());
        assertEquals("Database does not match saved Address state", ca.getState(), caChecker.getState());
        assertEquals("Database does not match saved Address city", ca.getCity(), caChecker.getCity());
        assertEquals("Database does not match saved Address zip", ca.getZipCode(), caChecker.getZipCode());
    }

}
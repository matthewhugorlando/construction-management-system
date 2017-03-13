package com.ironyard.repo;

import com.ironyard.data.CAddress;
import com.ironyard.data.CClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * Created by matthewhug on 3/13/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CClientRepoTest {

    @Autowired
    CClientRepo cClientRepo;

    @Test
    @Transactional
    public void saveClient(){

        CAddress ca = new CAddress("123 Client Test Street", "Orlando", "FL", "32819");

        CClient cc = new CClient("Test Name", "TestFirstName", "TestLastName", "4071234567", "test@client.com", ca);
        cClientRepo.save(cc);

        CClient ccCheck = cClientRepo.findOne(cc.getId());

        assertEquals("Database does not match saved Client name", cc.getName(), ccCheck.getName());
        assertEquals("Database does not match saved Client contact first name", cc.getContactFirstName(), ccCheck.getContactFirstName());
        assertEquals("Database does not match saved Client contact last name", cc.getContactLastName(), ccCheck.getContactLastName());
        assertEquals("Database does not match saved Client contact phone number", cc.getContactPhoneNumber(), ccCheck.getContactPhoneNumber());
        assertEquals("Database does not match saved Client contact email", cc.getContactEmail(), ccCheck.getContactEmail());
        assertEquals("Database does not match saved Address street", cc.getAddress().getStreet(), ccCheck.getAddress().getStreet());
        assertEquals("Database does not match saved Address state", cc.getAddress().getState(), ccCheck.getAddress().getState());
        assertEquals("Database does not match saved Address city", cc.getAddress().getCity(), ccCheck.getAddress().getCity());
        assertEquals("Database does not match saved Address zip", cc.getAddress().getZipCode(), ccCheck.getAddress().getZipCode());

    }
}
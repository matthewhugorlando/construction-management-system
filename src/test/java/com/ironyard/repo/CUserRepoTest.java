package com.ironyard.repo;

import com.ironyard.data.CAddress;
import com.ironyard.data.CUser;
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
public class CUserRepoTest {

    @Autowired
    CUserRepo cUserRepo;

    @Test
    @Transactional
    public void saveUser(){
        CAddress ca = new CAddress("123 User Test Street", "Orlando", "FL", "32819");

        CUser cu = new CUser("TestFirstName", "TestLastName", "TestUserName", "TestPassword", "test@email.com", "123-456-7890", "BASIC", true, ca);

        cUserRepo.save(cu);

        CUser cuCheck = cUserRepo.findOne(cu.getId());

        assertEquals("Database does not match saved User first name", cu.getFirstName(), cuCheck.getFirstName());
        assertEquals("Database does not match saved User last name", cu.getLastName(), cuCheck.getLastName());
        assertEquals("Database does not match saved User username", cu.getUsername(), cuCheck.getUsername());
        assertEquals("Database does not match saved User password", cu.getPassword(), cuCheck.getPassword());
        assertEquals("Database does not match saved User email", cu.getEmail(), cuCheck.getEmail());
        assertEquals("Database does not match saved User phone number", cu.getPhoneNumber(), cuCheck.getPhoneNumber());
        assertEquals("Database does not match saved User permission level", cu.getPermissionLevel(), cuCheck.getPermissionLevel());
        assertEquals("Database does not match saved User status", cu.isActive(), cuCheck.isActive());
        assertEquals("Database does not match saved User Address street", cu.getCurrentAddress().getStreet(), cuCheck.getCurrentAddress().getStreet());
        assertEquals("Database does not match saved User Address city", cu.getCurrentAddress().getCity(), cuCheck.getCurrentAddress().getCity());
        assertEquals("Database does not match saved User Address state", cu.getCurrentAddress().getState(), cuCheck.getCurrentAddress().getState());
        assertEquals("Database does not match saved User Address zip", cu.getCurrentAddress().getZipCode(), cuCheck.getCurrentAddress().getZipCode());

    }

}
package com.ironyard.repo;

import com.ironyard.data.CAddress;
import com.ironyard.data.CItemType;
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
public class CItemTypeRepoTest {

    @Autowired
    CItemTypeRepo cItemTypeRepo;

    @Autowired
    CUserRepo cUserRepo;

    @Test
    @Transactional
    public void saveItemType(){
        CAddress ca = new CAddress("123 User Test Street", "Orlando", "FL", "32819");

        CUser cu = new CUser("TestFirstName", "TestLastName", "TestUserName", "TestPassword", "test@email.com", "123-456-7890", "BASIC", true, ca);
        cUserRepo.save(cu);

        CItemType cit = new CItemType("Mastic", true, "Bucket", cu);
        cItemTypeRepo.save(cit);

        CItemType citCheck = cItemTypeRepo.findOne(cit.getId());


        assertEquals("Database does not match saved CItemType name", cit.getName(), citCheck.getName());
        assertEquals("Database does not match saved CItemType active", cit.isActive(), citCheck.isActive());
        assertEquals("Database does not match saved CItemType unit of measurement", cit.getUnitOfMeasurement(), citCheck.getUnitOfMeasurement());
        assertEquals("Database does not match saved CItemType created by User first name", cit.getCreatedBy().getFirstName(), citCheck.getCreatedBy().getFirstName());
        assertEquals("Database does not match saved CItemType created by User last name", cit.getCreatedBy().getLastName(), citCheck.getCreatedBy().getLastName());
        assertEquals("Database does not match saved CItemType created by User username", cit.getCreatedBy().getUsername(), citCheck.getCreatedBy().getUsername());
        assertEquals("Database does not match saved CItemType created by User password", cit.getCreatedBy().getPassword(), citCheck.getCreatedBy().getPassword());
        assertEquals("Database does not match saved CItemType created by User email", cit.getCreatedBy().getEmail(), citCheck.getCreatedBy().getEmail());
        assertEquals("Database does not match saved CItemType created by User phone number", cit.getCreatedBy().getPhoneNumber(), citCheck.getCreatedBy().getPhoneNumber());
        assertEquals("Database does not match saved CItemType created by User permission level", cit.getCreatedBy().getPermissionLevel(), citCheck.getCreatedBy().getPermissionLevel());
        assertEquals("Database does not match saved CItemType created by User status", cit.getCreatedBy().isActive(), citCheck.getCreatedBy().isActive());
        assertEquals("Database does not match saved CItemType created by User Address street", cit.getCreatedBy().getCurrentAddress().getStreet(), citCheck.getCreatedBy().getCurrentAddress().getStreet());
        assertEquals("Database does not match saved CItemType created by User Address city", cit.getCreatedBy().getCurrentAddress().getCity(), citCheck.getCreatedBy().getCurrentAddress().getCity());
        assertEquals("Database does not match saved CItemType created by User Address state", cit.getCreatedBy().getCurrentAddress().getState(), citCheck.getCreatedBy().getCurrentAddress().getState());
        assertEquals("Database does not match saved CItemType created by User Address zip", cit.getCreatedBy().getCurrentAddress().getZipCode(), citCheck.getCreatedBy().getCurrentAddress().getZipCode());

    }

}
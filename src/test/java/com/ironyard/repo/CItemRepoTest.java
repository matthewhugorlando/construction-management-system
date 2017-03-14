package com.ironyard.repo;

import com.ironyard.data.CAddress;
import com.ironyard.data.CItem;
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
public class CItemRepoTest {

    @Autowired
    CItemRepo cItemRepo;

    @Autowired
    CItemTypeRepo cItemTypeRepo;

    @Autowired
    CUserRepo cUserRepo;

    @Test
    @Transactional
    public void saveItem(){
        CAddress ca = new CAddress("123 Item Test Street", "Orlando", "FL", "32819");

        CUser cu = new CUser("TestFirstName", "TestLastName", "TestUserName", "TestPassword", "test@email.com", "123-456-7890", "BASIC", true, ca);
        cUserRepo.save(cu);

        CItemType cit = new CItemType("Mastic", true, "Bucket", 10.00, cu);
        cItemTypeRepo.save(cit);

        CItem ci = new CItem("PENDING", cit);
        cItemRepo.save(ci);

        CItem ciCheck = cItemRepo.findOne(ci.getId());

        assertEquals("Database does not match saved CItem status", ci.getStatus(), ciCheck.getStatus());
        assertEquals("Database does not match saved CItem CItemType name", ci.getItemType().getName(), ciCheck.getItemType().getName());
        assertEquals("Database does not match saved CItem CItemType active", ci.getItemType().isActive(), ciCheck.getItemType().isActive());
        assertEquals("Database does not match saved CItem CItemType unit of measurement", ci.getItemType().getUnitOfMeasurement(), ciCheck.getItemType().getUnitOfMeasurement());
        assertEquals("Database does not match saved CItem CItemType cost per unit", ci.getItemType().getCostPerUnit(), ciCheck.getItemType().getCostPerUnit(), 0.0001);
        assertEquals("Database does not match saved CItem CItemType created by User first name", ci.getItemType().getCreatedBy().getFirstName(), ciCheck.getItemType().getCreatedBy().getFirstName());
        assertEquals("Database does not match saved CItem CItemType created by User last name", ci.getItemType().getCreatedBy().getLastName(), ciCheck.getItemType().getCreatedBy().getLastName());
        assertEquals("Database does not match saved CItem CItemType created by User username", ci.getItemType().getCreatedBy().getUsername(), ciCheck.getItemType().getCreatedBy().getUsername());
        assertEquals("Database does not match saved CItem CItemType created by User password", ci.getItemType().getCreatedBy().getPassword(), ciCheck.getItemType().getCreatedBy().getPassword());
        assertEquals("Database does not match saved CItem CItemType created by User email", ci.getItemType().getCreatedBy().getEmail(), ciCheck.getItemType().getCreatedBy().getEmail());
        assertEquals("Database does not match saved CItem CItemType created by User phone number", ci.getItemType().getCreatedBy().getPhoneNumber(), ciCheck.getItemType().getCreatedBy().getPhoneNumber());
        assertEquals("Database does not match saved CItem CItemType created by User permission level", ci.getItemType().getCreatedBy().getPermissionLevel(), ciCheck.getItemType().getCreatedBy().getPermissionLevel());
        assertEquals("Database does not match saved CItem CItemType created by User status", ci.getItemType().getCreatedBy().isActive(), ciCheck.getItemType().getCreatedBy().isActive());
        assertEquals("Database does not match saved CItem CItemType created by User Address street", ci.getItemType().getCreatedBy().getCurrentAddress().getStreet(), ciCheck.getItemType().getCreatedBy().getCurrentAddress().getStreet());
        assertEquals("Database does not match saved CItem CItemType created by User Address city", ci.getItemType().getCreatedBy().getCurrentAddress().getCity(), ciCheck.getItemType().getCreatedBy().getCurrentAddress().getCity());
        assertEquals("Database does not match saved CItem CItemType created by User Address state", ci.getItemType().getCreatedBy().getCurrentAddress().getState(), ciCheck.getItemType().getCreatedBy().getCurrentAddress().getState());
        assertEquals("Database does not match saved CItem CItemType created by User Address zip", ci.getItemType().getCreatedBy().getCurrentAddress().getZipCode(), ciCheck.getItemType().getCreatedBy().getCurrentAddress().getZipCode());

    }

}
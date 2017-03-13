package com.ironyard.repo;

import com.ironyard.data.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public void saveEventUser(){
        Calendar c = Calendar.getInstance();
        Date now = c.getTime();

        CAddress ca1 = new CAddress("123 User Event Test Street 1", "Orlando", "FL", "32819");
        CAddress ca2 = new CAddress("123 User Event Test Street 2", "Orlando", "FL", "32819");

        CUser cu1 = new CUser("Test1FirstName", "Test1LastName", "Test1UserName", "Test1Password", "test1@email.com", "123-456-7890", "BASIC", true, ca1);
        CUser cu2 = new CUser("Test2FirstName", "Test2LastName", "Test2UserName", "Test2Password", "test2@email.com", "123-456-7890", "BASIC", true, ca2);

        CEvent ce = new CEvent(now, "CREATED", cu1, cu2);

        cEventRepo.save(ce);

        CEvent ceCheck = cEventRepo.findOne(ce.getId());

        assertEquals("Database matches entered event timestamp", ce.getTimeStamp(), ceCheck.getTimeStamp());
        assertEquals("Database matches entered CEvent event action", ce.getEventAction(), ceCheck.getEventAction());
        assertEquals("Database does not match saved CEvent created by User first name", ce.getCreatedBy().getFirstName(), ceCheck.getCreatedBy().getFirstName());
        assertEquals("Database does not match saved CEvent created by User last name", ce.getCreatedBy().getLastName(), ceCheck.getCreatedBy().getLastName());
        assertEquals("Database does not match saved CEvent created by User username", ce.getCreatedBy().getUsername(), ceCheck.getCreatedBy().getUsername());
        assertEquals("Database does not match saved CEvent created by User password", ce.getCreatedBy().getPassword(), ceCheck.getCreatedBy().getPassword());
        assertEquals("Database does not match saved CEvent created by User email", ce.getCreatedBy().getEmail(), ceCheck.getCreatedBy().getEmail());
        assertEquals("Database does not match saved CEvent created by User phone number", ce.getCreatedBy().getPhoneNumber(), ceCheck.getCreatedBy().getPhoneNumber());
        assertEquals("Database does not match saved CEvent created by User permission level", ce.getCreatedBy().getPermissionLevel(), ceCheck.getCreatedBy().getPermissionLevel());
        assertEquals("Database does not match saved CEvent created by User status", ce.getCreatedBy().isActive(), ceCheck.getCreatedBy().isActive());
        assertEquals("Database does not match saved CEvent created by User Address street", ce.getCreatedBy().getCurrentAddress().getStreet(), ceCheck.getCreatedBy().getCurrentAddress().getStreet());
        assertEquals("Database does not match saved CEvent created by User Address city", ce.getCreatedBy().getCurrentAddress().getCity(), ceCheck.getCreatedBy().getCurrentAddress().getCity());
        assertEquals("Database does not match saved CEvent created by User Address state", ce.getCreatedBy().getCurrentAddress().getState(), ceCheck.getCreatedBy().getCurrentAddress().getState());
        assertEquals("Database does not match saved CEvent created by User Address zip", ce.getCreatedBy().getCurrentAddress().getZipCode(), ceCheck.getCreatedBy().getCurrentAddress().getZipCode());
        assertEquals("Database does not match saved CEvent cUserEvent first name", ce.getcUserEvent().getFirstName(), ceCheck.getcUserEvent().getFirstName());
        assertEquals("Database does not match saved CEvent cUserEvent last name", ce.getcUserEvent().getLastName(), ceCheck.getcUserEvent().getLastName());
        assertEquals("Database does not match saved CEvent cUserEvent username", ce.getcUserEvent().getUsername(), ceCheck.getcUserEvent().getUsername());
        assertEquals("Database does not match saved CEvent cUserEvent password", ce.getcUserEvent().getPassword(), ceCheck.getcUserEvent().getPassword());
        assertEquals("Database does not match saved CEvent cUserEvent email", ce.getcUserEvent().getEmail(), ceCheck.getcUserEvent().getEmail());
        assertEquals("Database does not match saved CEvent cUserEvent phone number", ce.getcUserEvent().getPhoneNumber(), ceCheck.getcUserEvent().getPhoneNumber());
        assertEquals("Database does not match saved CEvent cUserEvent permission level", ce.getcUserEvent().getPermissionLevel(), ceCheck.getcUserEvent().getPermissionLevel());
        assertEquals("Database does not match saved CEvent cUserEvent status", ce.getcUserEvent().isActive(), ceCheck.getcUserEvent().isActive());
        assertEquals("Database does not match saved CEvent cUserEvent Address street", ce.getcUserEvent().getCurrentAddress().getStreet(), ceCheck.getcUserEvent().getCurrentAddress().getStreet());
        assertEquals("Database does not match saved CEvent cUserEvent Address city", ce.getcUserEvent().getCurrentAddress().getCity(), ceCheck.getcUserEvent().getCurrentAddress().getCity());
        assertEquals("Database does not match saved CEvent cUserEvent User Address state", ce.getcUserEvent().getCurrentAddress().getState(), ceCheck.getcUserEvent().getCurrentAddress().getState());
        assertEquals("Database does not match saved CEvent cUserEvent User Address zip", ce.getcUserEvent().getCurrentAddress().getZipCode(), ceCheck.getcUserEvent().getCurrentAddress().getZipCode());
    }

    @Test
    @Transactional
    public void saveEventItemType(){
        Calendar c = Calendar.getInstance();
        Date now = c.getTime();

        CAddress ca1 = new CAddress("123 ItemType Event Test Street 1", "Orlando", "FL", "32819");

        CUser cu1 = new CUser("Test1FirstName", "Test1LastName", "Test1UserName", "Test1Password", "test1@email.com", "123-456-7890", "BASIC", true, ca1);

        CItemType cit = new CItemType("12-inch Flex", true, "bag", 24.99, cu1);

        CEvent ce = new CEvent(now, "CREATED", cu1, cit);
        cEventRepo.save(ce);

        CEvent ceCheck = cEventRepo.findOne(ce.getId());

        assertEquals("Database matches entered event timestamp", ce.getTimeStamp(), ceCheck.getTimeStamp());
        assertEquals("Database matches entered CEvent event action", ce.getEventAction(), ceCheck.getEventAction());
        assertEquals("Database does not match saved CEvent created by User first name", ce.getCreatedBy().getFirstName(), ceCheck.getCreatedBy().getFirstName());
        assertEquals("Database does not match saved CEvent created by User last name", ce.getCreatedBy().getLastName(), ceCheck.getCreatedBy().getLastName());
        assertEquals("Database does not match saved CEvent created by User username", ce.getCreatedBy().getUsername(), ceCheck.getCreatedBy().getUsername());
        assertEquals("Database does not match saved CEvent created by User password", ce.getCreatedBy().getPassword(), ceCheck.getCreatedBy().getPassword());
        assertEquals("Database does not match saved CEvent created by User email", ce.getCreatedBy().getEmail(), ceCheck.getCreatedBy().getEmail());
        assertEquals("Database does not match saved CEvent created by User phone number", ce.getCreatedBy().getPhoneNumber(), ceCheck.getCreatedBy().getPhoneNumber());
        assertEquals("Database does not match saved CEvent created by User permission level", ce.getCreatedBy().getPermissionLevel(), ceCheck.getCreatedBy().getPermissionLevel());
        assertEquals("Database does not match saved CEvent created by User status", ce.getCreatedBy().isActive(), ceCheck.getCreatedBy().isActive());
        assertEquals("Database does not match saved CEvent created by User Address street", ce.getCreatedBy().getCurrentAddress().getStreet(), ceCheck.getCreatedBy().getCurrentAddress().getStreet());
        assertEquals("Database does not match saved CEvent created by User Address city", ce.getCreatedBy().getCurrentAddress().getCity(), ceCheck.getCreatedBy().getCurrentAddress().getCity());
        assertEquals("Database does not match saved CEvent created by User Address state", ce.getCreatedBy().getCurrentAddress().getState(), ceCheck.getCreatedBy().getCurrentAddress().getState());
        assertEquals("Database does not match saved CEvent created by User Address zip", ce.getCreatedBy().getCurrentAddress().getZipCode(), ceCheck.getCreatedBy().getCurrentAddress().getZipCode());

        assertEquals("Database does not match saved CEvent CItemType name", ce.getcItemTypeEvent().getName(), ce.getcItemTypeEvent().getName());
        assertEquals("Database does not match saved CEvent CItemType active", ce.getcItemTypeEvent().isActive(), ce.getcItemTypeEvent().isActive());
        assertEquals("Database does not match saved CEvent CItemType unit of measurement", ce.getcItemTypeEvent().getUnitOfMeasurement(), ce.getcItemTypeEvent().getUnitOfMeasurement());
        assertEquals("Database does not match saved CEvent CItemType cost per unit", ce.getcItemTypeEvent().getCostPerUnit(), ce.getcItemTypeEvent().getCostPerUnit(), 0.0001);
        assertEquals("Database does not match saved CEvent CItemType created by User first name", ce.getcItemTypeEvent().getCreatedBy().getFirstName(), ce.getcItemTypeEvent().getCreatedBy().getFirstName());
        assertEquals("Database does not match saved CEvent CItemType created by User last name", ce.getcItemTypeEvent().getCreatedBy().getLastName(), ce.getcItemTypeEvent().getCreatedBy().getLastName());
        assertEquals("Database does not match saved CEvent CItemType created by User username", ce.getcItemTypeEvent().getCreatedBy().getUsername(), ce.getcItemTypeEvent().getCreatedBy().getUsername());
        assertEquals("Database does not match saved CEvent CItemType created by User password", ce.getcItemTypeEvent().getCreatedBy().getPassword(), ce.getcItemTypeEvent().getCreatedBy().getPassword());
        assertEquals("Database does not match saved CEvent CItemType created by User email", ce.getcItemTypeEvent().getCreatedBy().getEmail(), ce.getcItemTypeEvent().getCreatedBy().getEmail());
        assertEquals("Database does not match saved CEvent CItemType created by User phone number", ce.getcItemTypeEvent().getCreatedBy().getPhoneNumber(), ce.getcItemTypeEvent().getCreatedBy().getPhoneNumber());
        assertEquals("Database does not match saved CEvent CItemType created by User permission level", ce.getcItemTypeEvent().getCreatedBy().getPermissionLevel(), ce.getcItemTypeEvent().getCreatedBy().getPermissionLevel());
        assertEquals("Database does not match saved CEvent CItemType created by User status", ce.getcItemTypeEvent().getCreatedBy().isActive(), ce.getcItemTypeEvent().getCreatedBy().isActive());
        assertEquals("Database does not match saved CEvent CItemType created by User Address street", ce.getcItemTypeEvent().getCreatedBy().getCurrentAddress().getStreet(), ce.getcItemTypeEvent().getCreatedBy().getCurrentAddress().getStreet());
        assertEquals("Database does not match saved CEvent CItemType created by User Address city", ce.getcItemTypeEvent().getCreatedBy().getCurrentAddress().getCity(), ce.getcItemTypeEvent().getCreatedBy().getCurrentAddress().getCity());
        assertEquals("Database does not match saved CEvent CItemType created by User Address state", ce.getcItemTypeEvent().getCreatedBy().getCurrentAddress().getState(), ce.getcItemTypeEvent().getCreatedBy().getCurrentAddress().getState());
        assertEquals("Database does not match saved CEvent CItemType created by User Address zip", ce.getcItemTypeEvent().getCreatedBy().getCurrentAddress().getZipCode(), ce.getcItemTypeEvent().getCreatedBy().getCurrentAddress().getZipCode());
    }

    @Test
    @Transactional
    public void saveEventItem() {
        Calendar c = Calendar.getInstance();
        Date now = c.getTime();

        CAddress ca1 = new CAddress("123 ItemType Event Test Street 1", "Orlando", "FL", "32819");

        CUser cu1 = new CUser("Test1FirstName", "Test1LastName", "Test1UserName", "Test1Password", "test1@email.com", "123-456-7890", "BASIC", true, ca1);

        CItemType cit = new CItemType("12-inch Flex", true, "bag", 24.99, cu1);

        CItem ci = new CItem(3, "PENDING", cit);

        CEvent ce = new CEvent(now, "CREATED", cu1, ci);
        cEventRepo.save(ce);

        CEvent ceCheck = cEventRepo.findOne(ce.getId());

        assertEquals("Database matches entered CEvent timestamp", ce.getTimeStamp(), ceCheck.getTimeStamp());
        assertEquals("Database matches entered CEvent event action", ce.getEventAction(), ceCheck.getEventAction());
        assertEquals("Database does not match saved CEvent created by User first name", ce.getCreatedBy().getFirstName(), ceCheck.getCreatedBy().getFirstName());
        assertEquals("Database does not match saved CEvent created by User last name", ce.getCreatedBy().getLastName(), ceCheck.getCreatedBy().getLastName());
        assertEquals("Database does not match saved CEvent created by User username", ce.getCreatedBy().getUsername(), ceCheck.getCreatedBy().getUsername());
        assertEquals("Database does not match saved CEvent created by User password", ce.getCreatedBy().getPassword(), ceCheck.getCreatedBy().getPassword());
        assertEquals("Database does not match saved CEvent created by User email", ce.getCreatedBy().getEmail(), ceCheck.getCreatedBy().getEmail());
        assertEquals("Database does not match saved CEvent created by User phone number", ce.getCreatedBy().getPhoneNumber(), ceCheck.getCreatedBy().getPhoneNumber());
        assertEquals("Database does not match saved CEvent created by User permission level", ce.getCreatedBy().getPermissionLevel(), ceCheck.getCreatedBy().getPermissionLevel());
        assertEquals("Database does not match saved CEvent created by User status", ce.getCreatedBy().isActive(), ceCheck.getCreatedBy().isActive());
        assertEquals("Database does not match saved CEvent created by User Address street", ce.getCreatedBy().getCurrentAddress().getStreet(), ceCheck.getCreatedBy().getCurrentAddress().getStreet());
        assertEquals("Database does not match saved CEvent created by User Address city", ce.getCreatedBy().getCurrentAddress().getCity(), ceCheck.getCreatedBy().getCurrentAddress().getCity());
        assertEquals("Database does not match saved CEvent created by User Address state", ce.getCreatedBy().getCurrentAddress().getState(), ceCheck.getCreatedBy().getCurrentAddress().getState());
        assertEquals("Database does not match saved CEvent created by User Address zip", ce.getCreatedBy().getCurrentAddress().getZipCode(), ceCheck.getCreatedBy().getCurrentAddress().getZipCode());

        assertEquals("Database does not match saved CItem quantity", ce.getcItemEvent().getQuantity(), ceCheck.getcItemEvent().getQuantity());
        assertEquals("Database does not match saved CItem status", ce.getcItemEvent().getStatus(), ceCheck.getcItemEvent().getStatus());
        assertEquals("Database does not match saved CItem CItemType name", ce.getcItemEvent().getItemType().getName(), ceCheck.getcItemEvent().getItemType().getName());
        assertEquals("Database does not match saved CItem CItemType active", ce.getcItemEvent().getItemType().isActive(), ceCheck.getcItemEvent().getItemType().isActive());
        assertEquals("Database does not match saved CItem CItemType unit of measurement", ce.getcItemEvent().getItemType().getUnitOfMeasurement(), ceCheck.getcItemEvent().getItemType().getUnitOfMeasurement());
        assertEquals("Database does not match saved CItem CItemType cost per unit", ce.getcItemEvent().getItemType().getCostPerUnit(), ceCheck.getcItemEvent().getItemType().getCostPerUnit(), 0.0001);
        assertEquals("Database does not match saved CItem CItemType created by User first name", ce.getcItemEvent().getItemType().getCreatedBy().getFirstName(), ceCheck.getcItemEvent().getItemType().getCreatedBy().getFirstName());
        assertEquals("Database does not match saved CItem CItemType created by User last name", ce.getcItemEvent().getItemType().getCreatedBy().getLastName(), ceCheck.getcItemEvent().getItemType().getCreatedBy().getLastName());
        assertEquals("Database does not match saved CItem CItemType created by User username", ce.getcItemEvent().getItemType().getCreatedBy().getUsername(), ceCheck.getcItemEvent().getItemType().getCreatedBy().getUsername());
        assertEquals("Database does not match saved CItem CItemType created by User password", ce.getcItemEvent().getItemType().getCreatedBy().getPassword(), ceCheck.getcItemEvent().getItemType().getCreatedBy().getPassword());
        assertEquals("Database does not match saved CItem CItemType created by User email", ce.getcItemEvent().getItemType().getCreatedBy().getEmail(), ceCheck.getcItemEvent().getItemType().getCreatedBy().getEmail());
        assertEquals("Database does not match saved CItem CItemType created by User phone number", ce.getcItemEvent().getItemType().getCreatedBy().getPhoneNumber(), ceCheck.getcItemEvent().getItemType().getCreatedBy().getPhoneNumber());
        assertEquals("Database does not match saved CItem CItemType created by User permission level", ce.getcItemEvent().getItemType().getCreatedBy().getPermissionLevel(), ceCheck.getcItemEvent().getItemType().getCreatedBy().getPermissionLevel());
        assertEquals("Database does not match saved CItem CItemType created by User status", ce.getcItemEvent().getItemType().getCreatedBy().isActive(), ceCheck.getcItemEvent().getItemType().getCreatedBy().isActive());
        assertEquals("Database does not match saved CItem CItemType created by User Address street", ce.getcItemEvent().getItemType().getCreatedBy().getCurrentAddress().getStreet(), ceCheck.getcItemEvent().getItemType().getCreatedBy().getCurrentAddress().getStreet());
        assertEquals("Database does not match saved CItem CItemType created by User Address city", ce.getcItemEvent().getItemType().getCreatedBy().getCurrentAddress().getCity(), ceCheck.getcItemEvent().getItemType().getCreatedBy().getCurrentAddress().getCity());
        assertEquals("Database does not match saved CItem CItemType created by User Address state", ce.getcItemEvent().getItemType().getCreatedBy().getCurrentAddress().getState(), ceCheck.getcItemEvent().getItemType().getCreatedBy().getCurrentAddress().getState());
        assertEquals("Database does not match saved CItem CItemType created by User Address zip", ce.getcItemEvent().getItemType().getCreatedBy().getCurrentAddress().getZipCode(), ceCheck.getcItemEvent().getItemType().getCreatedBy().getCurrentAddress().getZipCode());
    }

    @Test
    @Transactional
    public void saveWarehouseEvent(){
        Calendar c = Calendar.getInstance();
        Date now = c.getTime();

        CAddress ca = new CAddress("123 Warehouse Test Street", "Orlando", "FL", "32819");

        CWarehouse cw = new CWarehouse("Test Warehouse", ca, true);

        CAddress ca1 = new CAddress("123 ItemType Event Test Street 1", "Orlando", "FL", "32819");

        CUser cu1 = new CUser("Test1FirstName", "Test1LastName", "Test1UserName", "Test1Password", "test1@email.com", "123-456-7890", "BASIC", true, ca1);


        CEvent ce = new CEvent(now, "CREATED", cu1, cw);
        cEventRepo.save(ce);

        CEvent ceCheck = cEventRepo.findOne(ce.getId());

        assertEquals("Database matches entered CEvent timestamp", ce.getTimeStamp(), ceCheck.getTimeStamp());
        assertEquals("Database matches entered CEvent event action", ce.getEventAction(), ceCheck.getEventAction());
        assertEquals("Database does not match saved CEvent created by User first name", ce.getCreatedBy().getFirstName(), ceCheck.getCreatedBy().getFirstName());
        assertEquals("Database does not match saved CEvent created by User last name", ce.getCreatedBy().getLastName(), ceCheck.getCreatedBy().getLastName());
        assertEquals("Database does not match saved CEvent created by User username", ce.getCreatedBy().getUsername(), ceCheck.getCreatedBy().getUsername());
        assertEquals("Database does not match saved CEvent created by User password", ce.getCreatedBy().getPassword(), ceCheck.getCreatedBy().getPassword());
        assertEquals("Database does not match saved CEvent created by User email", ce.getCreatedBy().getEmail(), ceCheck.getCreatedBy().getEmail());
        assertEquals("Database does not match saved CEvent created by User phone number", ce.getCreatedBy().getPhoneNumber(), ceCheck.getCreatedBy().getPhoneNumber());
        assertEquals("Database does not match saved CEvent created by User permission level", ce.getCreatedBy().getPermissionLevel(), ceCheck.getCreatedBy().getPermissionLevel());
        assertEquals("Database does not match saved CEvent created by User status", ce.getCreatedBy().isActive(), ceCheck.getCreatedBy().isActive());
        assertEquals("Database does not match saved CEvent created by User Address street", ce.getCreatedBy().getCurrentAddress().getStreet(), ceCheck.getCreatedBy().getCurrentAddress().getStreet());
        assertEquals("Database does not match saved CEvent created by User Address city", ce.getCreatedBy().getCurrentAddress().getCity(), ceCheck.getCreatedBy().getCurrentAddress().getCity());
        assertEquals("Database does not match saved CEvent created by User Address state", ce.getCreatedBy().getCurrentAddress().getState(), ceCheck.getCreatedBy().getCurrentAddress().getState());
        assertEquals("Database does not match saved CEvent created by User Address zip", ce.getCreatedBy().getCurrentAddress().getZipCode(), ceCheck.getCreatedBy().getCurrentAddress().getZipCode());

        assertEquals("Database does not match saved CWarehouse name", ce.getcWarehouseEvent().getName(), ceCheck.getcWarehouseEvent().getName());
        assertEquals("Database does not match saved CWarehouse active status", ce.getcWarehouseEvent().isActive(), ceCheck.getcWarehouseEvent().isActive());
        assertEquals("Database does not match saved CWarehouse location street", ce.getcWarehouseEvent().getLocation().getStreet(), ceCheck.getcWarehouseEvent().getLocation().getStreet());
        assertEquals("Database does not match saved CWarehouse location state", ce.getcWarehouseEvent().getLocation().getState(), ceCheck.getcWarehouseEvent().getLocation().getState());
        assertEquals("Database does not match saved CWarehouse location city", ce.getcWarehouseEvent().getLocation().getCity(), ceCheck.getcWarehouseEvent().getLocation().getCity());
        assertEquals("Database does not match saved CWarehouse location zip code", ce.getcWarehouseEvent().getLocation().getZipCode(), ceCheck.getcWarehouseEvent().getLocation().getZipCode());




    }
}
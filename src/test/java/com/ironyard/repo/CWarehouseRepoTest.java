package com.ironyard.repo;

import com.ironyard.data.CAddress;
import com.ironyard.data.CWarehouse;
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
public class CWarehouseRepoTest {

    @Autowired
    CWarehouseRepo cWarehouseRepo;

    @Test
    @Transactional
    public void saveWarehouse(){
        CAddress ca = new CAddress("123 Warehouse Test Street", "Orlando", "FL", "32819");

        CWarehouse cw = new CWarehouse("Test Warehouse", ca, true);

        cWarehouseRepo.save(cw);

        CWarehouse cwCheck = cWarehouseRepo.findOne(cw.getId());

        assertEquals("Database does not match saved CWarehouse name", cw.getName(), cwCheck.getName());
        assertEquals("Database does not match saved CWarehouse active status", cw.isActive(), cwCheck.isActive());
        assertEquals("Database does not match saved User Address street", cw.getLocation().getStreet(), cwCheck.getLocation().getStreet());
        assertEquals("Database does not match saved User Address city", cw.getLocation().getCity(), cwCheck.getLocation().getCity());
        assertEquals("Database does not match saved User Address state", cw.getLocation().getState(), cwCheck.getLocation().getState());
        assertEquals("Database does not match saved User Address zip", cw.getLocation().getZipCode(), cwCheck.getLocation().getZipCode());

    }

}
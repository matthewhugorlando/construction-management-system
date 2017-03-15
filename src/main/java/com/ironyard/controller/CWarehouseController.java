package com.ironyard.controller;

import com.ironyard.data.CWarehouse;
import com.ironyard.repo.CWarehouseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by matthewhug on 3/14/17.
 */
@RestController
public class CWarehouseController {

    @Autowired
    CWarehouseRepo cWarehouseRepo;

    @RequestMapping(path = "/warehouse/new", method = RequestMethod.POST)
    public CWarehouse addWarehouse(@RequestParam String name, Boolean active){
        CWarehouse cw = new CWarehouse(name, active);
        cWarehouseRepo.save(cw);
        return cw;
    }

    @RequestMapping(path = "/warehouse/list", method = RequestMethod.GET)
    public Iterable<CWarehouse> listWarehouses(){
        return cWarehouseRepo.findAll();
    }

    @RequestMapping(path = "/warehouse/select", method = RequestMethod.GET)
    public CWarehouse findWarehouse(@RequestParam Long id){
        return cWarehouseRepo.findOne(id);
    }



}

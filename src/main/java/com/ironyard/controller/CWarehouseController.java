package com.ironyard.controller;

import com.ironyard.data.*;
import com.ironyard.dto.CJobDTO;
import com.ironyard.dto.CWarehouseDTO;
import com.ironyard.dto.InventoryDTO;
import com.ironyard.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by matthewhug on 3/14/17.
 */
@RestController
@RequestMapping(path = "/rest/warehouse")
public class CWarehouseController {

    @Autowired
    CItemBucketRepo cItemBucketRepo;

    @Autowired
    CItemRepo cItemRepo;

    @Autowired
    CItemTypeRepo cItemTypeRepo;

    @Autowired
    CWarehouseRepo cWarehouseRepo;

    @Autowired
    InvHolderRepo invHolderRepo;

    @RequestMapping(path = "/new", method = RequestMethod.POST)
    @Transactional
    public CWarehouse addWarehouse(@RequestBody CWarehouseDTO cwDTO){
        CWarehouse cw = new CWarehouse(cwDTO.getName(), cwDTO.getAddress(), true);
        cWarehouseRepo.save(cw);
        List<InventoryDTO> dtoI = cwDTO.getInventory();
        List<CItemBucket> cibs = new ArrayList<>();
        for(int i=0; i<dtoI.size(); i++){
            CItemType cit = cItemTypeRepo.findByName(dtoI.get(i).getType());
            if(dtoI.get(i).getFrom().equals("New")) {
                double totalCost = dtoI.get(i).getQty() * cit.getCostPerUnit();
                CItemBucket cib = new CItemBucket(dtoI.get(i).getQty(), dtoI.get(i).getStatus(), totalCost, cit, cw);
                List<CItem> cis = new ArrayList<>();
                for (int j = 0; j < dtoI.get(i).getQty(); j++) {
                    CItem ci = new CItem(dtoI.get(i).getStatus(), cit);
                    cItemRepo.save(ci);
                    cis.add(ci);
                }
                cib.setItems(cis);
                cItemBucketRepo.save(cib);
                cibs.add(cib);
            }
            else{
                InvHolder ih = invHolderRepo.findByName(dtoI.get(i).getFrom());
                CItemBucket transCib = cItemBucketRepo.findByStatusAndLocationIdAndBucketTypeId("On Site", ih.getId(), cit.getId());
                double totalCost = dtoI.get(i).getQty() * cit.getCostPerUnit();
                CItemBucket cib = new CItemBucket(dtoI.get(i).getQty(), dtoI.get(i).getStatus(), totalCost, cit, cw);
                List<CItem> cis = new ArrayList<>();
                for(int k=0;k<dtoI.get(i).getQty();k++){
                    CItem ci = transCib.getItems().get(transCib.getItems().size()-1);
                    ci.setStatus(dtoI.get(i).getStatus());
                    cItemRepo.save(ci);
                    cis.add(ci);
                    transCib.getItems().remove(transCib.getItems().size()-1);
                }

                if(transCib.getItems().size() == 0){
                    InvHolder ihCibToDel = invHolderRepo.findOne(transCib.getLocation().getId());
                    for(int l=0;l<ihCibToDel.getInventory().size();l++){
                        if(ihCibToDel.getInventory().get(l).getId() == transCib.getId()){
                            ihCibToDel.getInventory().remove(l);
                            invHolderRepo.save(ihCibToDel);
                        }
                    }
                    cItemBucketRepo.delete(transCib.getId());
                } else {
                    transCib.setQuantity(transCib.getQuantity() - dtoI.get(i).getQty());
                    transCib.setTotalCost(transCib.getBucketType().getCostPerUnit() * transCib.getQuantity());
                    cItemBucketRepo.save(transCib);
                }

                cib.setItems(cis);
                cItemBucketRepo.save(cib);
                cibs.add(cib);
            }
        }

        cw.setInventory(cibs);
        cWarehouseRepo.save(cw);

        return cw;
    }

    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public Iterable<CWarehouse> listWarehouses(){
        return cWarehouseRepo.findAll();
    }

    @RequestMapping(path = "/list/active", method = RequestMethod.GET)
    public Iterable<CWarehouse> listActiveWarehouses(){
        return cWarehouseRepo.findByActive(true);
    }

    @RequestMapping(path = "/select", method = RequestMethod.GET)
    public CWarehouse findWarehouse(@RequestParam Long id){
        return cWarehouseRepo.findOne(id);
    }

    @RequestMapping(path = "/toggle/active", method = RequestMethod.GET)
    public CWarehouse toggleWarehouseActive(@RequestParam Long id){
        CWarehouse cw = cWarehouseRepo.findOne(id);
        cw.setActive(!cw.isActive());
        cWarehouseRepo.save(cw);
        return cw;
    }

}

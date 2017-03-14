package com.ironyard.controller;

import com.ironyard.data.CItem;
import com.ironyard.data.CItemBucket;
import com.ironyard.data.CItemType;
import com.ironyard.data.InvHolder;
import com.ironyard.repo.CItemBucketRepo;
import com.ironyard.repo.CItemRepo;
import com.ironyard.repo.CItemTypeRepo;
import com.ironyard.repo.InvHolderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matthewhug on 3/14/17.
 */
@RestController
@RequestMapping(path = "/inventory")
public class InventoryController {

    @Autowired
    CItemBucketRepo cItemBucketRepo;

    @Autowired
    CItemRepo cItemRepo;

    @Autowired
    CItemTypeRepo cItemTypeRepo;

    @Autowired
    InvHolderRepo invHolderRepo;

    @RequestMapping(path = "/itembucket/select", method = RequestMethod.GET)
    public CItemBucket findBucket(@RequestParam long id){
        return cItemBucketRepo.findOne(id);
    }

    @RequestMapping(path = "/itemtype/new", method = RequestMethod.POST)
    public CItemType addItemType(@RequestParam String name,
                                 @RequestParam Boolean active,
                                 @RequestParam String unitOfMeasurement,
                                 @RequestParam Double costPerUnit){

        CItemType cit = new CItemType(name, active, unitOfMeasurement, costPerUnit);
        cItemTypeRepo.save(cit);
        return cit;
    }

    @RequestMapping(path = "/item/new", method = RequestMethod.POST)
    public CItemBucket addItems(@RequestParam Integer quantity,
                               @RequestParam String status,
                               @RequestParam String type,
                               @RequestParam String loc){

        List<CItem> cis = new ArrayList<>();

        CItemType cit = cItemTypeRepo.findByName(type);
        InvHolder location = invHolderRepo.findByName(loc);

        for(int i=0; i<quantity; i++){
            CItem ci = new CItem(status, cit);
            cItemRepo.save(ci);
            cis.add(ci);
        }

        Double totalCost = cis.size() * cit.getCostPerUnit();

        CItemBucket cib = new CItemBucket(quantity, status, totalCost, cit, cis, location);
        cItemBucketRepo.save(cib);
        if(location.getInventory() != null) {
            location.getInventory().add(cib);
        }else{
            List<CItemBucket> cibs = new ArrayList<>();
            cibs.add(cib);
            location.setInventory(cibs);
        }

        invHolderRepo.save(location);

        return cib;
    }

    @RequestMapping(path = "item/move", method = RequestMethod.POST)
    public CItemBucket moveItems(@RequestParam Long dLoc,
                                 @RequestParam String sLoc,
                                 @RequestParam String itemType,
                                 @RequestParam Integer quantity){

        InvHolder destLoc = invHolderRepo.findOne(dLoc);
        InvHolder startLoc = invHolderRepo.findByName(sLoc);
        CItemType cit = cItemTypeRepo.findByName(itemType);
        CItemBucket startCIB = cItemBucketRepo.findByStatusAndLocationIdAndBucketTypeId("ONSITE", startLoc.getId(), cit.getId());
        List<CItem> startCIs = startCIB.getItems();
        List<CItem> destCIs = new ArrayList<>();

        for(int i=0; i<quantity; i++){
            CItem ci = startCIs.get(startCIs.size() - 1);
            destCIs.add(ci);
            startCIs.remove(startCIs.size() - 1);
        }
        startCIB.setItems(startCIs);
        cItemBucketRepo.save(startCIB);

        Double totalCost = startCIB.getBucketType().getCostPerUnit()*quantity;

        CItemBucket destCIB = new CItemBucket(quantity, "DELIVER", totalCost, startCIB.getBucketType(), destCIs, destLoc);
        cItemBucketRepo.save(destCIB);

        return destCIB;

    }
}

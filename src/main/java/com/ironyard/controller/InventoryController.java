package com.ironyard.controller;

import com.ironyard.data.CItem;
import com.ironyard.data.CItemBucket;
import com.ironyard.data.CItemType;
import com.ironyard.data.InvHolder;
import com.ironyard.dto.CItemBucketDTO;
import com.ironyard.repo.CItemBucketRepo;
import com.ironyard.repo.CItemRepo;
import com.ironyard.repo.CItemTypeRepo;
import com.ironyard.repo.InvHolderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matthewhug on 3/14/17.
 */
@RestController
@RequestMapping(path = "/rest/inventory")
public class InventoryController {

    @Autowired
    CItemBucketRepo cItemBucketRepo;

    @Autowired
    CItemRepo cItemRepo;

    @Autowired
    CItemTypeRepo cItemTypeRepo;

    @Autowired
    InvHolderRepo invHolderRepo;

    @RequestMapping(path = "/select", method = RequestMethod.GET)
    public CItemBucket findBucket(@RequestParam long id){
        return cItemBucketRepo.findOne(id);
    }

    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public Iterable<CItemBucket> listBuckets(){
        return cItemBucketRepo.findAll();
    }

    @RequestMapping(path = "/itemtype/new", method = RequestMethod.POST)
    public CItemType addItemType(@RequestBody CItemType cit){
        cit.setActive(true);
        cItemTypeRepo.save(cit);
        return cit;
    }

    @RequestMapping(path = "/itemtype/find", method = RequestMethod.GET)
    public CItemType addItemType(@RequestParam Long citId){
        return cItemTypeRepo.findOne(citId);
    }

    @RequestMapping(path = "/itemtype/update", method = RequestMethod.POST)
    public CItemType updateItemType(@RequestBody CItemType cit){
        CItemType citToUpdate = cItemTypeRepo.findByName(cit.getName());
        cit.setId(citToUpdate.getId());
        cItemTypeRepo.save(cit);
        return cit;
    }

    @RequestMapping(path = "/itemtype/toggleActive", method = RequestMethod.GET)
    public CItemType toggleItemActive(@RequestParam Long citId){
        CItemType cit = cItemTypeRepo.findOne(citId);
        cit.setActive(!cit.isActive());
        cItemTypeRepo.save(cit);
        return cit;
    }

    @RequestMapping(path = "/itemtype/list", method = RequestMethod.GET)
    public Iterable<CItemType> listItemTypes(){
        return cItemTypeRepo.findAll();
    }

    @RequestMapping(path = "/item/new", method = RequestMethod.POST)
    public CItemBucket addItems(@RequestBody CItemBucketDTO dto){

        List<CItem> cis;

        long tId = Long.parseLong(dto.getTypeId());
        long lId = Long.parseLong(dto.getLocId());

        CItemType cit = cItemTypeRepo.findOne(tId);
        InvHolder location = invHolderRepo.findOne(lId);
        CItemBucket cib = cItemBucketRepo.findByStatusAndLocationIdAndBucketTypeId(cit.getName(), lId, tId);

        if(cib != null){
            cis = cib.getItems();
        }else{
            cis = new ArrayList<>();
            cib = new CItemBucket(dto.getQuantity(), dto.getStatus(), cit, location);
        }

        for(int i=0; i<dto.getQuantity(); i++){
            CItem ci = new CItem(dto.getStatus(), cit);
            cItemRepo.save(ci);
            cis.add(ci);
        }

        Double totalCost = cis.size() * cit.getCostPerUnit();

        cib.setTotalCost(totalCost);
        cib.setItems(cis);
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

    @RequestMapping(path = "/item/list", method = RequestMethod.GET)
    public Iterable<CItem> listItems(){
        return cItemRepo.findAll();
    }

    @RequestMapping(path = "/item/move", method = RequestMethod.POST)
    public CItemBucket moveItems(@RequestParam Long dLoc,
                                 @RequestParam String sLoc,
                                 @RequestParam String itemType,
                                 @RequestParam Integer quantity){

        InvHolder destLoc = invHolderRepo.findOne(dLoc);
        InvHolder startLoc = invHolderRepo.findByName(sLoc);
        CItemType cit = cItemTypeRepo.findByName(itemType);
        CItemBucket startCIB = cItemBucketRepo.findByStatusAndLocationIdAndBucketTypeId("On Site", startLoc.getId(), cit.getId());
        List<CItem> startCIs = startCIB.getItems();

        CItemBucket destCIB;
        List<CItem> destCIs;
        boolean destCIBExists = false;
        if(cItemBucketRepo.findByStatusAndLocationIdAndBucketTypeId("Pending Delivery", dLoc, cit.getId()) != null){
            destCIBExists=true;
            destCIB = cItemBucketRepo.findByStatusAndLocationIdAndBucketTypeId("Pending Delivery", dLoc, cit.getId());
            destCIs = destCIB.getItems();
        }else{
            destCIs = new ArrayList<>();
            destCIB = new CItemBucket();
        }

        for(int i=0; i<quantity; i++){
            CItem ci = startCIs.get(startCIs.size() - 1);
            ci.setStatus("Pending Delivery");
            destCIs.add(ci);
            startCIs.remove(startCIs.size() - 1);
        }
        startCIB.setItems(startCIs);
        cItemBucketRepo.save(startCIB);

        Double totalCost = startCIB.getBucketType().getCostPerUnit()*destCIs.size();

        if(destCIBExists){
            destCIB.setItems(destCIs);
        }else {
            destCIB = new CItemBucket(quantity, "Pending Delivery", totalCost, startCIB.getBucketType(), destCIs, destLoc);
        }
        cItemBucketRepo.save(destCIB);

        if(startLoc.getInventory() != null) {
            startLoc.getInventory().add(destCIB);
        }else{
            List<CItemBucket> cibs = new ArrayList<>();
            cibs.add(destCIB);
            startLoc.setInventory(cibs);
        }
        return destCIB;
    }

    @RequestMapping(path = "/item/update", method = RequestMethod.GET)
    public InvHolder updateItemStatus(@RequestParam Long id){

        CItemBucket startCIB = cItemBucketRepo.findOne(id);
        InvHolder ih = startCIB.getLocation();
        List<CItemBucket> cibs = ih.getInventory();
        List<CItem> startCIs = startCIB.getItems();
        CItemType cit = cItemTypeRepo.findByName(startCIB.getBucketType().getName());
        CItemBucket destCIB;
        List<CItem> destCIs;

        if(startCIB.getStatus().equals("Pending Delivery")){
            destCIB = cItemBucketRepo.findByStatusAndLocationIdAndBucketTypeId("On Site", ih.getId(), cit.getId());
            if(destCIB == null){
                destCIB = new CItemBucket(startCIB.getQuantity(), "On Site", cit, ih);
                destCIs = new ArrayList<>();
            }else{
                destCIs = destCIB.getItems();
            }
        }else if(startCIB.getStatus().equals("On Site")){
            destCIB = cItemBucketRepo.findByStatusAndLocationIdAndBucketTypeId("Used", startCIB.getLocation().getId(), startCIB.getBucketType().getId());
            if(destCIB == null){
                destCIB = new CItemBucket(startCIB.getQuantity(), "Used", cit, startCIB.getLocation());
                destCIs = new ArrayList<>();
            }else{
                destCIs = destCIB.getItems();
            }
        }else{
            destCIB = null;
            destCIs = null;
        }

        for(int i=0; i<startCIB.getQuantity(); i++){
            CItem ci = startCIs.get(i);
            ci.setStatus(destCIB.getStatus());
            destCIs.add(ci);
        }
        cibs.remove(startCIB);
        cItemBucketRepo.delete(startCIB);
        destCIB.setItems(destCIs);
        destCIB.setQuantity(destCIs.size());
        Double totalCost = cit.getCostPerUnit()*destCIs.size();
        destCIB.setTotalCost(totalCost);

        cItemBucketRepo.save(destCIB);

        cibs.add(destCIB);
        ih.setInventory(cibs);

        return ih;
    }


    @RequestMapping(path = "/find", method = RequestMethod.GET)
    public CItemBucket findForQty(@RequestParam String type, String from){
        InvHolder ih = invHolderRepo.findByName(from);
        CItemType cit = cItemTypeRepo.findByName(type);
        CItemBucket cib = new CItemBucket();
        if(from.equals("New")){
            cib = new CItemBucket(cit);
        }else {
            cib = cItemBucketRepo.findByStatusAndLocationIdAndBucketTypeId("On Site", ih.getId(), cit.getId());
        }
        return cib;
    }

    @RequestMapping(path = "/loc/find", method = RequestMethod.GET)
    public Iterable<CItemBucket> findByLoc(@RequestParam long locId){
        return cItemBucketRepo.findByLocationId(locId);
    }

    @RequestMapping(path = "/status/loc/find", method = RequestMethod.GET)
    public Iterable <CItemBucket> findByStatusAndLoc(@RequestParam long locId, @RequestParam String status){
        InvHolder ih = invHolderRepo.findOne(locId);
        return cItemBucketRepo.findByLocationAndStatus(ih, status);
    }

    @RequestMapping(path = "/type/loc/find", method = RequestMethod.GET)
    public Iterable <CItemBucket> findByLocAndType(@RequestParam long locId, @RequestParam String type){
        InvHolder ih = invHolderRepo.findOne(locId);
        CItemType cit = cItemTypeRepo.findByName(type);
        return cItemBucketRepo.findByLocationAndBucketType(ih, cit);
    }


}

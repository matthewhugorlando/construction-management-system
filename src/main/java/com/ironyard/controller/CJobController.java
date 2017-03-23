package com.ironyard.controller;

import com.ironyard.data.*;
import com.ironyard.dto.CJobDTO;
import com.ironyard.dto.CTaskDTO;
import com.ironyard.dto.InventoryDTO;
import com.ironyard.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by matthewhug on 3/14/17.
 */
@RestController
@RequestMapping(path = "/rest/job")
public class CJobController {

    @Autowired
    CClientRepo cClientRepo;

    @Autowired
    CItemBucketRepo cItemBucketRepo;

    @Autowired
    CItemRepo cItemRepo;

    @Autowired
    CItemTypeRepo cItemTypeRepo;

    @Autowired
    CJobRepo cJobRepo;

    @Autowired
    CTaskRepo cTaskRepo;

    @Autowired
    CUserRepo cUserRepo;

    @Autowired
    InvHolderRepo invHolderRepo;

    @RequestMapping(path = "/select", method = RequestMethod.GET)
    public CJob selectJob(@RequestParam Long id){
        return cJobRepo.findOne(id);
    }

//    @RequestMapping(path = "/new", method = RequestMethod.POST)
//    public CJob addJob(@RequestParam String name,
//                       @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate localStartDate,
//                       @RequestParam String status,
//                       @RequestParam Double jobPrice,
//                       @RequestParam String clientName){
//
//        Date startDate = java.sql.Date.valueOf(localStartDate);
//
//        CClient cc = cClientRepo.findByName(clientName);
//
//        CJob cj = new CJob(name, startDate, status, jobPrice, cc);
//        cJobRepo.save(cj);
//        return cj;
//    }

    @RequestMapping(path = "/new", method = RequestMethod.POST)
    @Transactional
    public CJob addJob(@RequestBody CJobDTO cjdto){
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        Date startDate = null;
        try {
            startDate = df.parse(cjdto.getStartDate());
            String newDateString = df.format(startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        CClient cc = cClientRepo.findOne(cjdto.getClId());
        CJob cj = new CJob(cjdto.getName(), startDate, cjdto.getStatus(), cjdto.getJobPrice(), cc, cjdto.getAddress());
        cJobRepo.save(cj);
        List<InventoryDTO> dtoI = cjdto.getInventory();
        List<CItemBucket> cibs = new ArrayList<>();
        for(int i=0; i<dtoI.size(); i++){
            CItemType cit = cItemTypeRepo.findByName(dtoI.get(i).getType());
            if(dtoI.get(i).getFrom().equals("New")) {
                double totalCost = dtoI.get(i).getQty() * cit.getCostPerUnit();
                CItemBucket cib = new CItemBucket(dtoI.get(i).getQty(), dtoI.get(i).getStatus(), totalCost, cit, cj);
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
                CItemBucket cib = new CItemBucket(dtoI.get(i).getQty(), dtoI.get(i).getStatus(), totalCost, cit, cj);
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

        cj.setInventory(cibs);
        cJobRepo.save(cj);

        return cj;
    }

    @RequestMapping(path = "/list", method = RequestMethod.GET)
    @Transactional
    public Iterable<CJob> listAllJobs(){
        return cJobRepo.findAll();
    }

    @RequestMapping(path = "/list/date", method = RequestMethod.GET)
    @Transactional
    public Iterable<CJob> listAllJobsByDay(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate d){
        Date day = java.sql.Date.valueOf(d);
        return cJobRepo.findByStartDate(day);
    }

    @RequestMapping(path = "/list/inprogress", method = RequestMethod.GET)
    @Transactional
    public Iterable<CJob> listInProgressJobs(){
        return cJobRepo.findByStatus("In Progress");
    }

    @RequestMapping(path = "/task/new", method = RequestMethod.POST)
    public CTask newTask(@RequestBody CTaskDTO ctDTO){

        Calendar c = Calendar.getInstance();
        Date now = c.getTime();
        CJob cj = cJobRepo.findOne(ctDTO.getJobId());
//        CUser cu = cUserRepo.findOne(cuId);

        List<CTask> cjcts = cj.getTasks();
        if(cjcts == null){
            cjcts = new ArrayList<>();
        }
        CTask ct = new CTask(ctDTO.getName(), ctDTO.getBody(), now, false, cj);
        cTaskRepo.save(ct);
        cjcts.add(ct);
        cj.setTasks(cjcts);
        cJobRepo.save(cj);
        return ct;
    }

    @RequestMapping(path = "/task/completed", method = RequestMethod.GET)
    public InvHolder markTaskCompleted(@RequestParam Long ctId, @RequestParam Long cjId){
        CTask ct = cTaskRepo.findOne(ctId);
        Calendar c = Calendar.getInstance();
        Date now = c.getTime();

        ct.setCompletedDate(now);
        ct.setCompleted(true);
        cTaskRepo.save(ct);

        return invHolderRepo.findOne(cjId);
    }

    @RequestMapping(path = "/task/delete", method = RequestMethod.GET)
    public InvHolder deleteTask(@RequestParam Long ctId, @RequestParam Long cjId){
        CTask ct = cTaskRepo.findOne(ctId);
        CJob cj = cJobRepo.findOne(ct.getJob().getId());
        List<CTask> cjTasks = cj.getTasks();

        for(int i=0; i< cjTasks.size(); i++){
            if(cjTasks.get(i).getId() == ctId){
                cjTasks.remove(i);
            }
        }

        cj.setTasks(cjTasks);
        cJobRepo.save(cj);
        cTaskRepo.delete(ctId);

        return invHolderRepo.findOne(cjId);
    }

    @RequestMapping(path = "/inventory/new", method = RequestMethod.POST)
    public InvHolder newInventory(@RequestBody InventoryDTO iDTO, @RequestParam Long cjId){
        InvHolder ih = invHolderRepo.findOne(cjId);
        CItemType cit = cItemTypeRepo.findByName(iDTO.getType());
        List<CItemBucket> cibs = ih.getInventory();
        CItemBucket cib = cItemBucketRepo.findByStatusAndLocationIdAndBucketTypeId(iDTO.getStatus(), cjId, cit.getId());
        List<CItem> cis = new ArrayList<>();
        double totalCost;
        boolean alreadyExists = false;
        if(cib == null) {
            cib = new CItemBucket(iDTO.getQty(), iDTO.getStatus(), cit, ih);
            totalCost = iDTO.getQty() * cit.getCostPerUnit();
            cib.setTotalCost(totalCost);
        }else{
            alreadyExists = true;
            cis = cib.getItems();
            totalCost = (iDTO.getQty() + cib.getItems().size()) * cit.getCostPerUnit();
            cib.setTotalCost(totalCost);
        }
        if(iDTO.getFrom().equals("New")) {
            for (int j = 0; j < iDTO.getQty(); j++) {
                CItem ci = new CItem(iDTO.getStatus(), cit);
                cItemRepo.save(ci);
                cis.add(ci);
            }
            cib.setItems(cis);
            cItemBucketRepo.save(cib);
        }
        else{
            InvHolder ihFrom = invHolderRepo.findByName(iDTO.getFrom());
            CItemBucket transCib = cItemBucketRepo.findByStatusAndLocationIdAndBucketTypeId("On Site", ihFrom.getId(), cit.getId());
            for(int k=0;k<iDTO.getQty();k++){
                CItem ci = transCib.getItems().get(transCib.getItems().size()-1);
                ci.setStatus(iDTO.getStatus());
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
                transCib.setQuantity(transCib.getQuantity() - iDTO.getQty());
                transCib.setTotalCost(transCib.getBucketType().getCostPerUnit() * transCib.getQuantity());
                cItemBucketRepo.save(transCib);
            }
        }

        if(!alreadyExists) {
            cib.setItems(cis);
            cib.setQuantity(cib.getItems().size());
            cibs.add(cib);
        }else{
            cib.setItems(cis);
            cib.setQuantity(cib.getItems().size());
        }
        cItemBucketRepo.save(cib);


        ih.setInventory(cibs);
        invHolderRepo.save(ih);
        return ih;
    }
}

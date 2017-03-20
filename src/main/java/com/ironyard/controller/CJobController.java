package com.ironyard.controller;

import com.ironyard.data.*;
import com.ironyard.dto.CJobDTO;
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
@RequestMapping(path = "/job")
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
//                cItemRepo.save(ci);
                    cis.add(ci);
                }
                cib.setItems(cis);
                cibs.add(cib);
            }
        }

        cj.setInventory(cibs);

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
    public CTask newTask(@RequestParam String name,
                         @RequestParam String body,
                         @RequestParam Long cuId,
                         @RequestParam Long cjId){

        Calendar c = Calendar.getInstance();
        Date now = c.getTime();
        CJob cj = cJobRepo.findOne(cjId);
        CUser cu = cUserRepo.findOne(cuId);

        List<CTask> cjcts = cj.getTasks();
        if(cjcts == null){
            cjcts = new ArrayList<>();
        }

        CTask ct = new CTask(name, body, now, false, cu, cj);
        cTaskRepo.save(ct);

        cjcts.add(ct);
        cj.setTasks(cjcts);

        cJobRepo.save(cj);

        return ct;
    }

}

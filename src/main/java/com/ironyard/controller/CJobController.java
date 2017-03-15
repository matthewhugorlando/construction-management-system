package com.ironyard.controller;

import com.ironyard.data.CClient;
import com.ironyard.data.CJob;
import com.ironyard.data.CTask;
import com.ironyard.data.CUser;
import com.ironyard.repo.CClientRepo;
import com.ironyard.repo.CJobRepo;
import com.ironyard.repo.CTaskRepo;
import com.ironyard.repo.CUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    CJobRepo cJobRepo;

    @Autowired
    CTaskRepo cTaskRepo;

    @Autowired
    CUserRepo cUserRepo;

    @RequestMapping(path = "/select", method = RequestMethod.GET)
    public CJob selectJob(@RequestParam Long id){
        return cJobRepo.findOne(id);
    }

    @RequestMapping(path = "/new", method = RequestMethod.POST)
    public CJob addJob(@RequestParam String name,
                       @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate localStartDate,
                       @RequestParam String status,
                       @RequestParam Double jobPrice,
                       @RequestParam String clientName){

        Date startDate = java.sql.Date.valueOf(localStartDate);

        CClient cc = cClientRepo.findByName(clientName);

        CJob cj = new CJob(name, startDate, status, jobPrice, cc);
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

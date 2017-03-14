package com.ironyard.controller;

import com.ironyard.data.CClient;
import com.ironyard.data.CJob;
import com.ironyard.repo.CClientRepo;
import com.ironyard.repo.CJobRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Date;

/**
 * Created by matthewhug on 3/14/17.
 */
@RestController
public class CJobController {

    @Autowired
    CClientRepo cClientRepo;

    @Autowired
    CJobRepo cJobRepo;

    @RequestMapping(path = "/jobs/select", method = RequestMethod.GET)
    public CJob selectJob(@RequestParam long id){
        return cJobRepo.findOne(id);
    }

    @RequestMapping(path = "/jobs/new", method = RequestMethod.POST)
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
}

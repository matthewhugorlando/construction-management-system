package com.ironyard.controller;

import com.ironyard.data.CClient;
import com.ironyard.repo.CClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by matthewhug on 3/14/17.
 */
@RestController
public class CClientController {

    @Autowired
    CClientRepo cClientRepo;

    @RequestMapping(path = "/client/new", method = RequestMethod.POST)
    public CClient addClient(@RequestParam String name,
                             @RequestParam String contactFirstName,
                             @RequestParam String contactLastName,
                             @RequestParam String contactPhoneNumber,
                             @RequestParam String contactEmail){

        CClient cc = new CClient(name, contactFirstName, contactLastName, contactPhoneNumber, contactEmail);
        cClientRepo.save(cc);

        return cc;
    }

    @RequestMapping(path = "/client/list", method = RequestMethod.GET)
    public Iterable<CClient> listClients(){
        return cClientRepo.findAll();
    }

    @RequestMapping(path = "/client/select", method = RequestMethod.GET)
    public CClient findClient(@RequestParam Long id){
        return cClientRepo.findOne(id);
    }
}

package com.ironyard.controller;

import com.ironyard.data.CClient;
import com.ironyard.repo.CAddressRepo;
import com.ironyard.repo.CClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * Created by matthewhug on 3/14/17.
 */
@RestController
@RequestMapping(path = "/client")
public class CClientController {

    @Autowired
    CAddressRepo cAddressRepo;

    @Autowired
    CClientRepo cClientRepo;

    @RequestMapping(path = "/new", method = RequestMethod.POST)
    @Transactional
    public CClient addClient(@RequestBody CClient cc){
        System.out.println("New Client Call");
        cClientRepo.save(cc);
        return cc;
    }

    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public Iterable<CClient> listClients(){
        return cClientRepo.findAll();
    }

    @RequestMapping(path = "/select", method = RequestMethod.GET)
    public CClient findClient(@RequestParam Long id){
        return cClientRepo.findOne(id);
    }
}

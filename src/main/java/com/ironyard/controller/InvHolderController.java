package com.ironyard.controller;

import com.ironyard.data.InvHolder;
import com.ironyard.repo.InvHolderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by matthewhug on 3/17/17.
 */
@RestController
@RequestMapping(path = "/invholder")
public class InvHolderController {

    @Autowired
    InvHolderRepo invHolderRepo;

    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public Iterable<InvHolder> listInvHolders(){
        return invHolderRepo.findAll();
    }
}

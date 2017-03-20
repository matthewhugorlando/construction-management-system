package com.ironyard.controller;

import com.ironyard.data.CItemBucket;
import com.ironyard.data.CItemType;
import com.ironyard.data.InvHolder;
import com.ironyard.repo.CItemBucketRepo;
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
 * Created by matthewhug on 3/17/17.
 */
@RestController
@RequestMapping(path = "/invholder")
public class InvHolderController {

    @Autowired
    CItemBucketRepo cItemBucketRepo;

    @Autowired
    CItemTypeRepo cItemTypeRepo;

    @Autowired
    InvHolderRepo invHolderRepo;

    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public Iterable<InvHolder> listInvHolders(){
        return invHolderRepo.findAll();
    }

    @RequestMapping(path = "/find", method = RequestMethod.GET)
    public List<InvHolder> findJobsWithType(@RequestParam String type){
        CItemType cit = cItemTypeRepo.findByName(type);
        Iterable<CItemBucket> cibs = cItemBucketRepo.findByBucketTypeAndStatus(cit, "On Site");
        List<InvHolder> ihswt = new ArrayList<>();
        for(CItemBucket cib : cibs){
            ihswt.add(cib.getLocation());
        }
        return ihswt;
    }
}

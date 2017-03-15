package com.ironyard.controller;

import com.ironyard.data.CAddress;
import com.ironyard.repo.CAddressRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by matthewhug on 3/15/17.
 */
@RestController
@RequestMapping("/address")
public class CAddressController {

    @Autowired
    CAddressRepo cAddressRepo;

    @RequestMapping(path = "/new", method = RequestMethod.POST)
    public CAddress addAddress(@RequestParam String street,
                               @RequestParam String city,
                               @RequestParam String state,
                               @RequestParam String zip){
        CAddress ca = new CAddress(street, city, state, zip);
        cAddressRepo.save(ca);
        return ca;
    }
}

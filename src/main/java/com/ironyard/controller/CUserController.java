package com.ironyard.controller;

import com.ironyard.data.CUser;
import com.ironyard.dto.CUserDTO;
import com.ironyard.repo.CUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Created by matthewhug on 3/14/17.
 */
@RestController
@RequestMapping(path = "/rest/user")
public class CUserController {

    @Autowired
    CUserRepo cUserRepo;

    @RequestMapping(path = "/new",
                    method = RequestMethod.POST,
                    consumes = MediaType.ALL_VALUE)
    public CUser addUser(@RequestBody CUserDTO cuDTO){
        System.out.println("Called addUser()");
        CUser cu = new CUser(cuDTO.getFirstName(), cuDTO.getLastName(), cuDTO.getUsername(), cuDTO.getPassword(), cuDTO.getEmail(), cuDTO.getPhoneNumber(), cuDTO.getPermissionLevel(), true);
        cUserRepo.save(cu);
        return cu;
    }

    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public Iterable<CUser> listUsers(){
        return cUserRepo.findAll();
    }

    @RequestMapping(path = "/select", method = RequestMethod.GET)
    public CUser findUser(@RequestParam Long id){
        return cUserRepo.findOne(id);
    }


}

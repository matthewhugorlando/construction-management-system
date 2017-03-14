package com.ironyard.controller;

import com.ironyard.data.CUser;
import com.ironyard.repo.CUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Created by matthewhug on 3/14/17.
 */
@RestController
public class CUserController {

    @Autowired
    CUserRepo cUserRepo;

    @RequestMapping(path = "/user/new",
                    method = RequestMethod.POST)
    public CUser addUser(@RequestParam String firstName,
                         @RequestParam String lastName,
                         @RequestParam String username,
                         @RequestParam String password,
                         @RequestParam String email,
                         @RequestParam String phoneNumber,
                         @RequestParam String permissionLevel,
                         @RequestParam Boolean active){
        CUser newUser = new CUser(firstName, lastName, username, password, email, phoneNumber, permissionLevel, active);

        cUserRepo.save(newUser);
        return newUser;
    }


}

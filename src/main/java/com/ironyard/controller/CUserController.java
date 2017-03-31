package com.ironyard.controller;

import com.ironyard.data.CJob;
import com.ironyard.data.CUser;
import com.ironyard.dto.CUserDTO;
import com.ironyard.repo.CJobRepo;
import com.ironyard.repo.CUserRepo;
import com.ironyard.security.TokenMaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by matthewhug on 3/14/17.
 */
@RestController
@RequestMapping(path = "/rest/user")
public class CUserController {

    @Autowired
    CJobRepo cJobRepo;

    @Autowired
    CUserRepo cUserRepo;

    @RequestMapping(path = "/new",
                    method = RequestMethod.POST,
                    consumes = MediaType.ALL_VALUE)
    public CUser addUser(@RequestBody CUserDTO cuDTO){
        System.out.println("Called addUser()");
        CUser cu = new CUser(cuDTO.getFirstName(), cuDTO.getLastName(), cuDTO.getUsername(), cuDTO.getPassword(), cuDTO.getEmail(), cuDTO.getPhoneNumber(), cuDTO.getPermissionLevel(), true, cuDTO.getCurrentAddress());
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

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public TokenMaster attemptLogin(HttpServletResponse res,
                                    @RequestParam String u,
                                    @RequestParam String p){
        TokenMaster t = new TokenMaster();
        CUser cu = cUserRepo.findByUsernameAndPassword(u, p);
        if(cu == null){
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        String tok = null;
        try {
            tok = t.generateToken(cu);
        } catch (Exception e) {
            e.printStackTrace();
        }
        t.setToken(tok);
        return t;
    }

    @RequestMapping(path = "/jobs", method = RequestMethod.GET)
    public Iterable<CJob> findJobs(@RequestParam Long cuId){
        return cJobRepo.findByStatusAndWorkedOnById("In Progress", cuId);
    }

    @RequestMapping(path = "/me", method = RequestMethod.GET)
    public CUser findMe(@RequestHeader(value="x-authorization-key") String token){
        TokenMaster tm = new TokenMaster();
        return cUserRepo.findOne(tm.getUserIdFromToken(token));
    }

    @RequestMapping(path = "/update",
            method = RequestMethod.POST,
            consumes = MediaType.ALL_VALUE)
    public CUser updateUser(@RequestBody CUserDTO cuDTO){
        CUser cu = cUserRepo.findOne(cuDTO.getId());
        cu.setFirstName(cuDTO.getFirstName());
        cu.setLastName(cuDTO.getLastName());
        cu.setUsername(cuDTO.getUsername());
        cu.setPassword(cuDTO.getPassword());
        cu.setPhoneNumber(cuDTO.getPhoneNumber());
        cu.setEmail(cuDTO.getEmail());
        cu.setCurrentAddress(cuDTO.getCurrentAddress());
        cUserRepo.save(cu);
        return cu;
    }

}

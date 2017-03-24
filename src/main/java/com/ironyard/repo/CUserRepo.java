package com.ironyard.repo;

import com.ironyard.data.CUser;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by matthewhug on 3/13/17.
 */
public interface CUserRepo extends PagingAndSortingRepository<CUser, Long>{
    public CUser findByUsernameAndPassword(String username, String password);
}

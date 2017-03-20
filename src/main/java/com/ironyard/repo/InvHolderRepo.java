package com.ironyard.repo;

import com.ironyard.data.InvHolder;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by matthewhug on 3/13/17.
 */
public interface InvHolderRepo extends PagingAndSortingRepository<InvHolder, Long> {
    public InvHolder findByName(String name);
}

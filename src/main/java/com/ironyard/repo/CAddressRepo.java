package com.ironyard.repo;

import com.ironyard.data.CAddress;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by matthewhug on 3/13/17.
 */
public interface CAddressRepo extends PagingAndSortingRepository<CAddress, Long> {
}

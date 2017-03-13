package com.ironyard.repo;

import com.ironyard.data.CClient;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by matthewhug on 3/13/17.
 */
public interface CClientRepo extends PagingAndSortingRepository<CClient, Long> {
}

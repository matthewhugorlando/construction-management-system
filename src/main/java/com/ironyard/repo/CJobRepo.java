package com.ironyard.repo;

import com.ironyard.data.CJob;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by matthewhug on 3/13/17.
 */
public interface CJobRepo extends PagingAndSortingRepository<CJob, Long> {
}

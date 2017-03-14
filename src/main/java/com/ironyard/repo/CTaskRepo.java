package com.ironyard.repo;

import com.ironyard.data.CTask;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by matthewhug on 3/14/17.
 */
public interface CTaskRepo extends PagingAndSortingRepository<CTask, Long>{
}

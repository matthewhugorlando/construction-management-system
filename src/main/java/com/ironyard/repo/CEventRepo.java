package com.ironyard.repo;

import com.ironyard.data.CEvent;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by matthewhug on 3/13/17.
 */
public interface CEventRepo extends PagingAndSortingRepository<CEvent, Long>{
}

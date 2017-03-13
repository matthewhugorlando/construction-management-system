package com.ironyard.repo;

import com.ironyard.data.CVehicle;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by matthewhug on 3/13/17.
 */
public interface CVehicleRepo extends PagingAndSortingRepository<CVehicle, Long> {
}

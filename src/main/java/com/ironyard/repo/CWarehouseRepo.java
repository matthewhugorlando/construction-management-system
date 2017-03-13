package com.ironyard.repo;

import com.ironyard.data.CWarehouse;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by matthewhug on 3/13/17.
 */
public interface CWarehouseRepo extends PagingAndSortingRepository<CWarehouse, Long> {
}

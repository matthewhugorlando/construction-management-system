package com.ironyard.repo;

import com.ironyard.data.CItemType;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by matthewhug on 3/13/17.
 */
public interface CItemTypeRepo extends PagingAndSortingRepository<CItemType, Long> {
}

package com.ironyard.repo;

import com.ironyard.data.CItem;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by matthewhug on 3/13/17.
 */
public interface CItemRepo extends PagingAndSortingRepository<CItem, Long>{
}

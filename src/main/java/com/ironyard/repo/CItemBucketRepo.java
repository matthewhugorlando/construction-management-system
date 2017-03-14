package com.ironyard.repo;

import com.ironyard.data.CItemBucket;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by matthewhug on 3/14/17.
 */
public interface CItemBucketRepo extends PagingAndSortingRepository<CItemBucket, Long> {
    public CItemBucket findByStatusAndLocationIdAndBucketTypeId(String status, long locationId, long bucketTypeId);
}

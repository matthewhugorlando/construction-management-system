package com.ironyard.repo;

import com.ironyard.data.CItemBucket;
import com.ironyard.data.CItemType;
import com.ironyard.data.InvHolder;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by matthewhug on 3/14/17.
 */
public interface CItemBucketRepo extends PagingAndSortingRepository<CItemBucket, Long> {
    public CItemBucket findByStatusAndLocationIdAndBucketTypeId(String status, long locationId, long bucketTypeId);
    public Iterable<CItemBucket> findByBucketTypeAndStatus(CItemType cit, String status);
    public Iterable<CItemBucket> findByBucketType(CItemType cit);
    public Iterable<CItemBucket> findByLocationId(long locationId);
    public Iterable<CItemBucket> findByLocationAndStatus(InvHolder location, String status);
    public Iterable<CItemBucket> findByLocationAndBucketType(InvHolder location, CItemType bucketType);
}

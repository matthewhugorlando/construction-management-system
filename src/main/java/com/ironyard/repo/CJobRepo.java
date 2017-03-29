package com.ironyard.repo;

import com.ironyard.data.CClient;
import com.ironyard.data.CJob;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;

/**
 * Created by matthewhug on 3/13/17.
 */
public interface CJobRepo extends PagingAndSortingRepository<CJob, Long> {
    public Iterable<CJob> findByStartDate(Date startDate);
    public Iterable<CJob> findByStatus(String status);
    public Iterable<CJob> findByClientAndStatus(CClient cClient, String Status);
    public Iterable<CJob> findByStatusAndWorkedOnById(String status, Long workedOnById);
}

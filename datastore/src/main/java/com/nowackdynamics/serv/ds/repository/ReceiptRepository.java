package com.nowackdynamics.serv.ds.repository;

import com.nowackdynamics.serv.ds.entity.Receipt;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface ReceiptRepository extends MongoRepository<Receipt, UUID> {

    List<Receipt> findByClaimedBy(UUID claimedBy);

    void deleteByClaimedBy(UUID claimedBy);
}

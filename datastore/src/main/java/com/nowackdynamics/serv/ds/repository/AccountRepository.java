package com.nowackdynamics.serv.ds.repository;

import com.nowackdynamics.serv.ds.entity.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface AccountRepository extends MongoRepository<Account, UUID> {
}

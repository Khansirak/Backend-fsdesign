package com.backerhicks.fsdesignMongo.repositories;

import com.backerhicks.fsdesignMongo.entities.LogicChain;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LogicChainRepository extends MongoRepository<LogicChain, String> {
}

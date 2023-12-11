package com.backerhicks.fsdesignMongo.repositories;


import com.backerhicks.fsdesignMongo.entities.StepChain;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StepChainRepository extends MongoRepository<StepChain, String> {
}

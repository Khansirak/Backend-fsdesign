package com.backerhicks.fsdesignMongo.repositories;

import com.backerhicks.fsdesignMongo.entities.Signals;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SignalsRepository extends MongoRepository<Signals, String> {
}

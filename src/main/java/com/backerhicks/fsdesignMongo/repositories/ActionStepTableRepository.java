package com.backerhicks.fsdesignMongo.repositories;

import com.backerhicks.fsdesignMongo.entities.ActionStepTable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ActionStepTableRepository  extends MongoRepository<ActionStepTable, String> {
}

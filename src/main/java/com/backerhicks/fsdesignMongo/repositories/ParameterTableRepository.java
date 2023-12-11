package com.backerhicks.fsdesignMongo.repositories;

import com.backerhicks.fsdesignMongo.entities.ParameterTable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ParameterTableRepository extends MongoRepository<ParameterTable, String> {


}

package com.backerhicks.fsdesignMongo.repositories;

import com.backerhicks.fsdesignMongo.entities.AlarmProps;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface AlarmPropsRepository extends MongoRepository<AlarmProps, String> {
}

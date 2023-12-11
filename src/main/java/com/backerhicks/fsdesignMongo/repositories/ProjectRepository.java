package com.backerhicks.fsdesignMongo.repositories;


import com.backerhicks.fsdesignMongo.entities.Project;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProjectRepository extends MongoRepository<Project, String> {

    @Query(value = "{}", fields = "{ 'projectInformation' : 1}")
    List<Project> findProjectsWithOnlyProjectInformation();

}
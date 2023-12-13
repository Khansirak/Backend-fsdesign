package com.backerhicks.fsdesignMongo.repositories;


import com.backerhicks.fsdesignMongo.entities.StepChain;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface StepChainRepository extends MongoRepository<StepChain, String> {

    @Query("{ 'recipe_id' : ?0, 'id' : ?1}")
    StepChain findByRecipeIdAndId(String recipeId, String id);

}

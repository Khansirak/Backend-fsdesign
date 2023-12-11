package com.backerhicks.fsdesignMongo.repositories;

import com.backerhicks.fsdesignMongo.entities.Recipe;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RecipeRepository extends MongoRepository<Recipe, String> {
}

package com.backerhicks.fsdesignMongo.services;

import com.backerhicks.fsdesignMongo.entities.LogicChain;
import com.backerhicks.fsdesignMongo.entities.Project;
import com.backerhicks.fsdesignMongo.entities.Recipe;
import com.backerhicks.fsdesignMongo.entities.Signals;
import com.backerhicks.fsdesignMongo.repositories.LogicChainRepository;
import com.backerhicks.fsdesignMongo.repositories.ProjectRepository;
import com.backerhicks.fsdesignMongo.repositories.RecipeRepository;

import lombok.AllArgsConstructor;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.lang.management.LockInfo;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LogicService {
    private RecipeRepository recipeRepository;
    private ProjectRepository projectRepository;
    private LogicChainRepository logicChainRepository;
    public Project createLogic(String id, String payload) {

        Document bsonDocument = Document.parse((payload));
        List<Recipe> listOfRecipes = recipeRepository.findAll();
        Recipe recipeOptional = listOfRecipes.stream()
                .filter(recipe -> recipe.getId().equals(id))
                .findFirst()
                .orElse(null);
        try{
            if (recipeOptional != null) {

                    LogicChain signalTable = new LogicChain();
                    signalTable.setId(id);
                    signalTable.setRecipe_id(id);
                    signalTable.setLogic(bsonDocument);
                    signalTable = logicChainRepository.save(signalTable);

                 recipeOptional.setLogicChain(signalTable);
                recipeRepository.save(recipeOptional);
                    return null;
            }
        }
        catch (Exception e) {
            return null;
        }

        return null;
    }
}

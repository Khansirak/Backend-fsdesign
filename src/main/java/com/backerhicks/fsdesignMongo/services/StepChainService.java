package com.backerhicks.fsdesignMongo.services;

import com.backerhicks.fsdesignMongo.entities.Project;
import com.backerhicks.fsdesignMongo.entities.Recipe;
import com.backerhicks.fsdesignMongo.entities.StepChain;
import com.backerhicks.fsdesignMongo.repositories.ProjectRepository;
import com.backerhicks.fsdesignMongo.repositories.RecipeRepository;
import com.backerhicks.fsdesignMongo.repositories.StepChainRepository;
import lombok.AllArgsConstructor;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class StepChainService {
    private RecipeRepository recipeRepository;
    private ProjectRepository projectRepository;
    private StepChainRepository stepChainRepository;

    public Project createStepChain(String id, String flow, String chainArray) {
        Document bsonDocument = Document.parse((flow));
        List<Recipe> listOfRecipes = recipeRepository.findAll();
        Recipe recipeOptional = listOfRecipes.stream()
                .filter(recipe -> recipe.getId().equals(id))
                .findFirst()
                .orElse(null);
                if (recipeOptional !=null ) {
                    StepChain StepChainNew = recipeOptional.getStepChain();
                    if (StepChainNew==null){
                        StepChain stepchainTable = new StepChain();
                        stepchainTable.setId(id);
                        stepchainTable.setRecipe_id(id);
                        stepchainTable.setStepChain(bsonDocument);
                        stepchainTable.setActionStepArray(chainArray);
                        stepchainTable = stepChainRepository.save(stepchainTable);
                        recipeOptional.setStepChain(stepchainTable);
                        recipeRepository.save(recipeOptional);
                    }
                    else{
                        StepChainNew.setStepChain(bsonDocument);
                        StepChainNew.setActionStepArray(chainArray);
                        StepChainNew = stepChainRepository.save(StepChainNew);
                        recipeOptional.setStepChain(StepChainNew);
                         recipeRepository.save(recipeOptional);
                    }
                }
        return null;
    }
    public Project createStepChainHoldAbort(String id,String holdabortid, String flow, String chainArray) {
        Document bsonDocument = Document.parse((flow));
        List<Recipe> listOfRecipes = recipeRepository.findAll();
        Recipe recipeOptional = listOfRecipes.stream()
                .filter(recipe -> recipe.getId().equals(id))
                .findFirst()
                .orElse(null);
        if (recipeOptional !=null ) {
            Set<StepChain> StepChainNew =  recipeOptional.getStepChainHoldAbort();
                StepChain stepchainTable = new StepChain();
                stepchainTable.setId(holdabortid);
                stepchainTable.setRecipe_id(id);
                stepchainTable.setStepChain(bsonDocument);
                stepchainTable.setActionStepArray(chainArray);
                stepchainTable = stepChainRepository.save(stepchainTable);
                 StepChainNew.add(stepchainTable);
                recipeOptional.setStepChainHoldAbort(StepChainNew);
                recipeRepository.save(recipeOptional);

        }
        return null;
    }

    public StepChain getStepChainHoldAbort(String id, String holdabortid) {
            Optional<Recipe> recipe=recipeRepository.findById(id);
        if (recipe !=null ) {
            StepChain stepchain =stepChainRepository.findByRecipeIdAndId(id,holdabortid);
            return stepchain;
        }
        return null;
    }
}

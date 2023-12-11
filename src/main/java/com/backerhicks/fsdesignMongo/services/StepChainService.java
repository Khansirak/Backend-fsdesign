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

//    public Project createStepArray(String id, String payload) {
//        String projectid= "64e49be16e87794364abafb4";
//
//        Document bsonDocument = Document.parse((payload));
//        Project project = projectRepository.findById(projectid).orElse(null);
//        try{
//            if (project != null) {
//                Optional<Recipe> recipeOptional = project.getRecipe().stream()
//                        .filter(recipe -> recipe.getId().equals(id))
//                        .findFirst();
//
//                if (recipeOptional.isPresent()) {
//
//                    //try
//                    StepChain StepChainNew = recipeOptional.orElseThrow().getStepChain();
//
//                    if (StepChainNew==null){
//                        StepChain stepchainTable = new StepChain();
//                        stepchainTable.setId(id);
//                        stepchainTable.setRecipe_id(id);
//                        stepchainTable.setActionStepArray(bsonDocument);
//                        stepchainTable = stepChainRepository.save(stepchainTable);
//                        Recipe recipeToUpdate = recipeOptional.get();
//                        recipeToUpdate.setStepChain(stepchainTable);
//                        recipeRepository.save(recipeToUpdate);
//
//                    }
//                    else{
//
//                        StepChainNew.setStepChain(bsonDocument);
//                        StepChainNew = stepChainRepository.save(StepChainNew);
//
//                        Recipe recipeToUpdate = recipeOptional.get();
//                        recipeToUpdate.setStepChain(StepChainNew);
//                        recipeRepository.save(recipeToUpdate);
//                    }
//                    projectRepository.save(project);
//                    return project;
//
//                }
//            }
//
//        }
//        catch (Exception e) {
//            return project;
//        }
//        return project;
//    }


}

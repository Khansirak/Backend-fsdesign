package com.backerhicks.fsdesignMongo.services;

import com.backerhicks.fsdesignMongo.entities.Project;
import com.backerhicks.fsdesignMongo.entities.Recipe;
import com.backerhicks.fsdesignMongo.repositories.*;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@Service
public class RecipeService {
    private RecipeRepository recipeRepository;
    private ProjectRepository projectRepository;
  private GraphRepository graphRepository;
  private LogicChainRepository logicChainRepository;
  private ParameterTableRepository parameterTableRepository;
  private SignalsRepository signalsRepository;
  private AlarmPropsRepository alarmPropsRepository;
  private StepChainRepository stepChainRepository;
    public Project createDescriptionInformation(String id, String payload) {
        ///TODO all the ids
        List<Recipe> listOfRecipes = recipeRepository.findAll();
        Recipe recipeOptional = listOfRecipes.stream()
                .filter(recipe -> recipe.getId().equals(id))
                .findFirst()
                .orElse(null);

            if (recipeOptional !=null) {

                recipeOptional.setDescription(payload);
               recipeRepository.save(recipeOptional);

                return null;
            }
return null;
    }

    public List<String> createRecipe(String id) {
        Project project = projectRepository.findById(id).orElse(null);
        if (project != null) {
            Recipe newRecipe= new Recipe();
            newRecipe=recipeRepository.save(newRecipe);
            Set<Recipe> recipeList= project.getRecipe();
            recipeList.add(newRecipe);
            project.setRecipe(recipeList);
            projectRepository.save(project);
            List<String> recipeIds= new ArrayList<>();
            recipeIds.add(newRecipe.getId());
            return recipeIds;
        }

        return null;
    }

    public String DeleteRecipe(String idP, String idR) {

        Project project = projectRepository.findById(idP).orElse(null);
        Recipe therecipe = recipeRepository.findById(idR).orElse(null);
        if (project != null) {
//
//            Set<Recipe> recipeList = project.getRecipe();
//            Recipe lastRecipe = null;
//
//            // Iterate through the set to find the last Recipe
//            for (Recipe recipe : recipeList) {
//                lastRecipe = recipe;
//            }
//            recipeList.remove(recipeList.size() - 1);
//            assert lastRecipe != null;
//            String idRecipe= lastRecipe.getId();
//
//            project.setRecipe(recipeList);

            Set<Recipe> operations = therecipe.getSubOperations();
            operations.forEach(recipeRepository::delete);
            operations.forEach(this::deletePhase);
            for (Recipe subOperation : operations) {
                //TODO stepchain
                graphRepository.deleteById(subOperation.getId());
                logicChainRepository.deleteById(subOperation.getId());
                stepChainRepository.deleteById(subOperation.getId());
                parameterTableRepository.deleteById(subOperation.getId());
                signalsRepository.deleteById(subOperation.getId());
                alarmPropsRepository.deleteById(subOperation.getId());
            }
            projectRepository.save(project);
            recipeRepository.deleteById(idR);
//TODO delete the stepchain tables
//
            graphRepository.deleteById(idR);
            logicChainRepository.deleteById(idR);
            stepChainRepository.deleteById(idR);
            parameterTableRepository.deleteById(idR);
            signalsRepository.deleteById(idR);
            alarmPropsRepository.deleteById(idR);
        }
        return null;
    }

    private void deletePhase(Recipe recipe) {
        Set<Recipe> phases = recipe.getSubPhases();
        phases.forEach(recipeRepository::delete);
        for (Recipe subOperation : phases) {
            //TODO stepchain
            graphRepository.deleteById(subOperation.getId());
            logicChainRepository.deleteById(subOperation.getId());
            stepChainRepository.deleteById(subOperation.getId());
            parameterTableRepository.deleteById(subOperation.getId());
            signalsRepository.deleteById(subOperation.getId());
            alarmPropsRepository.deleteById(subOperation.getId());
        }
    }

    public List<Recipe> getRecipeIds() {
        return recipeRepository.findAll();
    }

    public List<String> createOperation(String id, String recipeid) {

        List<Recipe> listOfRecipes = recipeRepository.findAll();
        Recipe foundRecipe = listOfRecipes.stream()
                .filter(recipe -> recipe.getId().equals(recipeid))
                .findFirst()
                .orElse(null);
            if ( foundRecipe !=null){
                Recipe newRecipe= new Recipe();
                newRecipe=recipeRepository.save(newRecipe);
                Set<Recipe> recipeList= foundRecipe.getSubOperations();
                recipeList.add(newRecipe);
                foundRecipe.setSubOperations(recipeList);
                foundRecipe=recipeRepository.save(foundRecipe);
                List<String> recipeIds= new ArrayList<>();
                recipeIds.add(foundRecipe.getId());
                return recipeIds;
            }

        return null;
    }

    public String deleteOperation( String operationId) {
        Recipe theoperation = recipeRepository.findById(operationId).orElse(null);

        Set<Recipe> operations = theoperation.getSubPhases();
        operations.forEach(this::deletePhase);
            recipeRepository.deleteById(operationId);
////TODO delete the stepchain tables
            graphRepository.deleteById(operationId);
            logicChainRepository.deleteById(operationId);
            stepChainRepository.deleteById(operationId);
            parameterTableRepository.deleteById(operationId);
            signalsRepository.deleteById(operationId);
            alarmPropsRepository.deleteById(operationId);

        return null;
    }

    public List<String> createPhase(String id, String recipeid) {
        Project project = projectRepository.findById(id).orElse(null);
//        if (project != null) {
            List<Recipe> listOfRecipes = recipeRepository.findAll();
            Recipe foundRecipe = listOfRecipes.stream()
                    .filter(recipe -> recipe.getId().equals(recipeid))
                    .findFirst()
                    .orElse(null);
          if (foundRecipe != null){
              Recipe newRecipe= new Recipe();
              newRecipe=recipeRepository.save(newRecipe);
              Set<Recipe> recipeList= foundRecipe.getSubPhases();
              recipeList.add(newRecipe);
              foundRecipe.setSubPhases(recipeList);
              foundRecipe=recipeRepository.save(foundRecipe);
              List<String> recipeIds= new ArrayList<>();
              recipeIds.add(foundRecipe.getId());
              return recipeIds;
          }

//        }
        return null;
    }

    public String deletePhase(String phaseId) {
        recipeRepository.deleteById(phaseId);
////TODO delete the stepchain tables
//
        graphRepository.deleteById(phaseId);
        logicChainRepository.deleteById(phaseId);
        stepChainRepository.deleteById(phaseId);
        parameterTableRepository.deleteById(phaseId);
        signalsRepository.deleteById(phaseId);
        alarmPropsRepository.deleteById(phaseId);

        return null;

    }

    public List<String> createPartialRecipe(String id) {
        Project project = projectRepository.findById(id).orElse(null);
        if (project != null) {
            Recipe newRecipe= new Recipe();
            newRecipe=recipeRepository.save(newRecipe);
            Set<Recipe> recipeList= project.getPartialRecipe();
            recipeList.add(newRecipe);
            project.setPartialRecipe(recipeList);
            projectRepository.save(project);
            List<String> recipeIds= new ArrayList<>();
            recipeIds.add(newRecipe.getId());
            return recipeIds;
        }
        return null;

    }

    public Optional<Recipe> getRecipe(String id) {
        return recipeRepository.findById(id);
    }
}

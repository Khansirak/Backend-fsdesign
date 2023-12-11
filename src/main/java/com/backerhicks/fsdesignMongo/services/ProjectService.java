package com.backerhicks.fsdesignMongo.services;

import com.backerhicks.fsdesignMongo.entities.Project;
import com.backerhicks.fsdesignMongo.entities.Recipe;
import com.backerhicks.fsdesignMongo.repositories.*;
import lombok.AllArgsConstructor;
import org.bson.Document;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@Service
public class ProjectService {
    private ProjectRepository projectRepository;
    private RecipeRepository recipeRepository;
    private GraphRepository graphRepository;
    private LogicChainRepository logicChainRepository;
    private ParameterTableRepository parameterTableRepository;
    private SignalsRepository signalsRepository;
    private AlarmPropsRepository alarmPropsRepository;
    private StepChainRepository stepChainRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    public Project createProject() {
        Project newproject=new Project();
        newproject= projectRepository.save(newproject);

        return newproject;
    }
    public Project deleteProject(String id) {
        Optional<Project> projectOptional = projectRepository.findById(id);
        projectOptional.ifPresent(project -> {
            // Delete associated recipes and partial recipes
            Set<Recipe> recipes = project.getRecipe();
            Set<Recipe> partialRecipes = project.getPartialRecipe();

            recipes.forEach(this::deleteRecipe);
            partialRecipes.forEach(this::deletePartialRecipe);

            // Delete the project
            projectRepository.deleteById(id);
        });
        return null;
    }
    private void deleteRecipe(Recipe recipe) {
        Set<Recipe> operations = recipe.getSubOperations();
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
//TODO stepchain

        graphRepository.deleteById(recipe.getId());
        logicChainRepository.deleteById(recipe.getId());
        stepChainRepository.deleteById(recipe.getId());
        parameterTableRepository.deleteById(recipe.getId());
        signalsRepository.deleteById(recipe.getId());
        alarmPropsRepository.deleteById(recipe.getId());
        recipeRepository.delete(recipe);

    }
    private void deletePartialRecipe(Recipe recipe) {
        Set<Recipe> operations = recipe.getSubOperations();
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

        graphRepository.deleteById(recipe.getId());
        logicChainRepository.deleteById(recipe.getId());
        stepChainRepository.deleteById(recipe.getId());
        parameterTableRepository.deleteById(recipe.getId());
        signalsRepository.deleteById(recipe.getId());
        alarmPropsRepository.deleteById(recipe.getId());
        recipeRepository.delete(recipe);
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

    public Project createProjectInformation(String payload, String id){

        // Create a BSON Document from the JSON string
        Document bsonDocument = Document.parse((payload));
        Project project = projectRepository.findById(id).orElse(null);

            Document projectInfoOld= (Document) project.getProjectInformation();
            if (projectInfoOld==null){
                project.setProjectInformation( bsonDocument);
                project=projectRepository.save(project);
                return new ModelMapper().map(project, Project.class);
            }else{
                for (String key : bsonDocument.keySet()) {
                    Object oldObj = bsonDocument.get(key);
                    Object newObj = bsonDocument.get(key);
                    if (oldObj instanceof Document && newObj instanceof Document) {
                        // If both values are documents, recursively merge them
                        Document oldSubDocument = (Document) oldObj;
                        Document newSubDocument = (Document) newObj;
                        for (String subKey : newSubDocument.keySet()) {
                            oldSubDocument.put(subKey, newSubDocument.get(subKey));
                        }
                    } else {
                        if (oldObj != null) {
                            projectInfoOld.put(key, newObj);
                        } else {

                            projectInfoOld.put(key, newObj);
                        }
                    }
                }
                project.setProjectInformation( projectInfoOld);
                project=projectRepository.save(project);
                return new ModelMapper().map(project, Project.class);
            }



    }


    public List<Project> getProjectsWithOnlyProjectInformation() {
        List<Project> informations= projectRepository.findProjectsWithOnlyProjectInformation();

        return informations;
    }

    public List<String> getProjectIds() {
        List<Project> projects = projectRepository.findAll();
        List<String> projectIds = new ArrayList<>();
        // Extract project IDs as strings and store them in a list

        for (Project project : projects) {
            projectIds.add(project.getId());
        }
        return projectIds;
    }


}

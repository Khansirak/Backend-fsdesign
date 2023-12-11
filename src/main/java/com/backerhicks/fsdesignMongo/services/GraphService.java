package com.backerhicks.fsdesignMongo.services;

import com.backerhicks.fsdesignMongo.entities.Graph;
import com.backerhicks.fsdesignMongo.entities.Project;
import com.backerhicks.fsdesignMongo.entities.Recipe;
import com.backerhicks.fsdesignMongo.repositories.GraphRepository;
import com.backerhicks.fsdesignMongo.repositories.ProjectRepository;
import com.backerhicks.fsdesignMongo.repositories.RecipeRepository;
import com.backerhicks.fsdesignMongo.repositories.StepChainRepository;
import lombok.AllArgsConstructor;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GraphService {
    private GraphRepository graphRepository;
    private RecipeRepository recipeRepository;
    private ProjectRepository projectRepository;
    private StepChainRepository stepChainRepository;
    public Project createGraph(String id, String payload, String array) {

        Document bsonDocument = Document.parse(payload);

        List<Recipe> listOfRecipes = recipeRepository.findAll();
        Recipe recipeOptional = listOfRecipes.stream()
                .filter(recipe -> recipe.getId().equals(id))
                .findFirst()
                .orElse(null);

                if (recipeOptional!= null) {

                    //try
                    Graph GraphNew = recipeOptional.getGraphRecipe();

                    if (GraphNew==null){
                        Graph graph = new Graph();
                        graph.setId(id);
                        graph.setRecipe_id(id);
                        graph.setGraph(bsonDocument);
                        graph.setArray(array);
                        graph = graphRepository.save(graph);
                        recipeOptional.setGraphRecipe(graph);
                        recipeRepository.save(recipeOptional);
                    }
                    else{
                        GraphNew.setGraph(bsonDocument);
                        GraphNew.setArray(array);
                        GraphNew = graphRepository.save(GraphNew);
                        recipeOptional.setGraphRecipe(
                                GraphNew);
                        recipeRepository.save(recipeOptional);
                    }

                }



        return null;
    }

}

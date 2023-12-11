package com.backerhicks.fsdesignMongo.services;

import com.backerhicks.fsdesignMongo.entities.ParameterTable;
import com.backerhicks.fsdesignMongo.entities.Project;
import com.backerhicks.fsdesignMongo.entities.Recipe;
import com.backerhicks.fsdesignMongo.repositories.ParameterTableRepository;
import com.backerhicks.fsdesignMongo.repositories.ProjectRepository;
import com.backerhicks.fsdesignMongo.repositories.RecipeRepository;
import lombok.AllArgsConstructor;
import org.bson.BsonDocument;
import org.bson.Document;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor

public class ParameterTableService {
    private ParameterTableRepository parametertableRepository;
    private ProjectRepository projectRepository;
    private RecipeRepository recipeRepository;
    public Project createTable(String id, String payload){
        Document bsonDocument = Document.parse((payload));
        try{
//    if (project != null) {
            List<Recipe> listOfRecipes = recipeRepository.findAll();
            Recipe recipeOptional = listOfRecipes.stream()
                    .filter(recipe -> recipe.getId().equals(id))
                    .findFirst()
                    .orElse(null);
        if (recipeOptional != null) {
            //try
            ParameterTable parameterTableNew = recipeOptional.getParameterTables();
            if (parameterTableNew == null) {
                System.out.println("test");
                ParameterTable parameterTable = new ParameterTable();
                parameterTable.setId(id);
                parameterTable.setRecipe_id(id);
                parameterTable.setParameterData(bsonDocument);
                parameterTable = parametertableRepository.save(parameterTable);
                recipeOptional.setParameterTables(parameterTable);
                recipeOptional = recipeRepository.save(recipeOptional);
                return null;


            }else{

            Document oldbsonDocuemnt = (Document) parameterTableNew.getParameterData();
// Iterate through the keys in newbsonDocument
            for (String key : bsonDocument.keySet()) {
                // Check if the key exists in oldbsonDocuemnt and newbsonDocument
                if (oldbsonDocuemnt.containsKey(key) && bsonDocument.containsKey(key)) {
                    Document oldSubDocument = oldbsonDocuemnt.get(key, Document.class);
                    Document newSubDocument = bsonDocument.get(key, Document.class);
                    // Iterate through the keys in newSubDocument
                    for (String subKey : newSubDocument.keySet()) {
                        // Update the corresponding value in oldSubDocument with the value from newSubDocument
                        oldSubDocument.put(subKey, newSubDocument.get(subKey));
                    }
                    // Update the corresponding key in oldbsonDocuemnt with the updated sub-document
                    oldbsonDocuemnt.put(key, oldSubDocument);
                }
            }
System.out.println(oldbsonDocuemnt);
            ParameterTable parameterTable = new ParameterTable();
            parameterTable.setId(id);
            parameterTable.setRecipe_id(id);
            parameterTable.setParameterData(oldbsonDocuemnt);
            parameterTable = parametertableRepository.save(parameterTable);

                recipeOptional.setParameterTables(parameterTable);
                recipeOptional = recipeRepository.save(recipeOptional);

                return null;
        }
        }


//    }
}
     catch (Exception e) {
                return null;
            }



        return null;

    }
}
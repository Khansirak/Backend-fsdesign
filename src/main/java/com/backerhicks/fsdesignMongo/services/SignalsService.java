package com.backerhicks.fsdesignMongo.services;

import com.backerhicks.fsdesignMongo.entities.ParameterTable;
import com.backerhicks.fsdesignMongo.entities.Project;
import com.backerhicks.fsdesignMongo.entities.Recipe;
import com.backerhicks.fsdesignMongo.entities.Signals;
import com.backerhicks.fsdesignMongo.repositories.ProjectRepository;
import com.backerhicks.fsdesignMongo.repositories.RecipeRepository;
import com.backerhicks.fsdesignMongo.repositories.SignalsRepository;
import lombok.AllArgsConstructor;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor

public class SignalsService {
    private ProjectRepository projectRepository;
    private RecipeRepository recipeRepository;
    private SignalsRepository signalsRepository;
    public Project createTableSignals(String id,String payload) {


        Document bsonDocument = Document.parse((payload));

        List<Recipe> listOfRecipes = recipeRepository.findAll();
        Recipe recipeOptional = listOfRecipes.stream()
                .filter(recipe -> recipe.getId().equals(id))
                .findFirst()
                .orElse(null);


                if (recipeOptional!=null) {

                    //try
                    Signals signalTableNew = recipeOptional.getSignalData();
                    if (signalTableNew == null) {
                        Signals signalTable = new Signals();
                        signalTable.setId(id);
                        signalTable.setRecipe_id(id);
                        signalTable.setSignalData(bsonDocument);
                        signalTable = signalsRepository.save(signalTable);

                        recipeOptional.setSignalData(signalTable);
                        recipeOptional = recipeRepository.save(recipeOptional);

                        return null;
                    } else {


                    Document oldbsonDocuemnt = (Document) signalTableNew.getSignalData();
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

                    Signals signalTable = new Signals();
                    signalTable.setId(id);
                    signalTable.setRecipe_id(id);
                    signalTable.setSignalData(oldbsonDocuemnt);
                    signalTable = signalsRepository.save(signalTable);

                        recipeOptional.setSignalData(signalTable);
                        recipeOptional = recipeRepository.save(recipeOptional);

                        return null;
                }
            }
        return null;

    }
}

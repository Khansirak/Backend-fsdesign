package com.backerhicks.fsdesignMongo.services;

import com.backerhicks.fsdesignMongo.entities.*;
import com.backerhicks.fsdesignMongo.repositories.AlarmPropsRepository;
import com.backerhicks.fsdesignMongo.repositories.ProjectRepository;
import com.backerhicks.fsdesignMongo.repositories.RecipeRepository;
import com.backerhicks.fsdesignMongo.repositories.SignalsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor

public class AlarmPropsService {
    private ProjectRepository projectRepository;
    private RecipeRepository recipeRepository;
    private AlarmPropsRepository alarmPropsRepository;

    public Project createTableAlarmProps(String id, String payload)
        {

            Document bsonDocument = Document.parse((payload));


            List<Recipe> listOfRecipes = recipeRepository.findAll();
            Recipe recipeOptional = listOfRecipes.stream()
                    .filter(recipe -> recipe.getId().equals(id))
                    .findFirst()
                    .orElse(null);

                    if (recipeOptional!=null) {

                        AlarmProps alarmpropsTableNew = recipeOptional.getAlarmPropsData();
                        if(alarmpropsTableNew==null){
                            AlarmProps alarmpropTable = new AlarmProps();
                            alarmpropTable.setId(id);
                            alarmpropTable.setRecipe_id(id);
                            alarmpropTable.setAlarmpropsData( bsonDocument);

                            alarmpropTable = alarmPropsRepository.save(alarmpropTable);

                            recipeOptional.setAlarmPropsData(alarmpropTable);
                            recipeOptional = recipeRepository.save(recipeOptional);

                            return null;
                        }
                        Document oldbsonDocuemnt= (Document) alarmpropsTableNew.getAlarmpropsData();

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

                        AlarmProps alarmpropTable = new AlarmProps();
                        alarmpropTable.setId(id);
                        alarmpropTable.setRecipe_id(id);
                        alarmpropTable.setAlarmpropsData(oldbsonDocuemnt);
                        alarmpropTable = alarmPropsRepository.save(alarmpropTable);


                        recipeOptional.setAlarmPropsData(alarmpropTable);
                        recipeOptional = recipeRepository.save(recipeOptional);

                        return null;
                    }



            return null;

        }

}

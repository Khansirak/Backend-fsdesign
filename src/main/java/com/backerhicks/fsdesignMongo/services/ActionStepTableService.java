package com.backerhicks.fsdesignMongo.services;

import com.backerhicks.fsdesignMongo.entities.ActionStepTable;
import com.backerhicks.fsdesignMongo.entities.Project;
import com.backerhicks.fsdesignMongo.entities.Recipe;
import com.backerhicks.fsdesignMongo.entities.StepChain;
import com.backerhicks.fsdesignMongo.repositories.ActionStepTableRepository;
import com.backerhicks.fsdesignMongo.repositories.ProjectRepository;
import com.backerhicks.fsdesignMongo.repositories.RecipeRepository;
import com.backerhicks.fsdesignMongo.repositories.StepChainRepository;
import lombok.AllArgsConstructor;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor

public class ActionStepTableService {
    private RecipeRepository recipeRepository;
    private ProjectRepository projectRepository;
    private  StepChainRepository stepChainRepository;
    private ActionStepTableRepository actionStepTableRepository;
    public Project createActionStepTable(String id, String payload) {

        Document bsonDocument = Document.parse((payload));

        List<Recipe> listOfRecipes = recipeRepository.findAll();
        Recipe recipeOptional = listOfRecipes.stream()
                .filter(recipe -> recipe.getId().equals(id))
                .findFirst()
                .orElse(null);


                if (recipeOptional!= null) {

                    StepChain StepChainNew = recipeOptional.getStepChain();

                    Set<ActionStepTable> actionStepTable=StepChainNew.getActionStepTables();


                    Optional<ActionStepTable> optionalActionStepTable = actionStepTable.stream()
                            .filter(actionstep -> actionstep.getId().equals(bsonDocument.getString("id")))
                            .findFirst();

                    /////DOING TEH UPDTATE
                    Document bsonDocumentNew = (Document) bsonDocument.get("table");

                    if (optionalActionStepTable.isPresent()) {
                    Document bsonDocumenOld = (Document) optionalActionStepTable.get().getActionstepTable();

                    //                    // Iterate through the keys in newbsonDocument
                    for (String key : bsonDocumentNew.keySet()) {
                        Object oldObj = bsonDocumenOld.get(key);
                        Object newObj = bsonDocumentNew.get(key);

                        if (oldObj instanceof Document && newObj instanceof Document) {
                            // If both values are documents, recursively merge them
                            Document oldSubDocument = (Document) oldObj;
                            Document newSubDocument = (Document) newObj;

                            for (String subKey : newSubDocument.keySet()) {
                                oldSubDocument.put(subKey, newSubDocument.get(subKey));
                            }
                        } else {
                            // If the key exists in old document, update it with the new value
                            if (oldObj != null) {
                                bsonDocumenOld.put(key, newObj);
                            } else {
                                // If the key does not exist in old document, add it with the new value
                                bsonDocumenOld.put(key, newObj);
                            }
                        }
                    }
                        bsonDocumentNew=bsonDocumenOld;
                    }

                        ActionStepTable actionstepTable = new ActionStepTable();
                        actionstepTable.setId(bsonDocument.getString("id"));
                        actionstepTable.setRecipe_id(id);
                        actionstepTable.setStepchain_id(StepChainNew.getId());
                        actionstepTable.setActionstepTable(bsonDocumentNew);
                        actionstepTable = actionStepTableRepository.save(actionstepTable);

/////TO BE FIXED
                        actionStepTable.add(actionstepTable);
                        stepChainRepository.save(StepChainNew);

                    return null;

                }

        return null;

    }
}

package com.backerhicks.fsdesignMongo.controllers;
import com.backerhicks.fsdesignMongo.entities.Project;
import com.backerhicks.fsdesignMongo.entities.Recipe;
import com.backerhicks.fsdesignMongo.repositories.LogicChainRepository;
import com.backerhicks.fsdesignMongo.repositories.ProjectRepository;
import com.backerhicks.fsdesignMongo.services.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("api/project")
public class ProjectController {

    private ProjectService projectservice;
    private RecipeService recipeservice;
    @Autowired
    private MongoTemplate mongoTemplate;
    private ProjectRepository projectRepository;
    private LogicChainRepository logicChainRepository;
    private SignalsService signalService;
    private ParameterTableService prametertableService;
    private AlarmPropsService alarmPropsService;
    private LogicService logicService;
    private StepChainService stepChainService;
    private ActionStepTableService actionStepTableService;
    private GraphService graphService;
//private Imageservice ImageService;
    @PostMapping
    ResponseEntity<Project> CreateProject() {
        try {
            Project projectinformation = projectservice.createProject();
            return ResponseEntity.status(HttpStatus.OK).body(projectinformation);
        } catch (Exception e) {
            return null;
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> DeleteProject(@PathVariable String id) {
        try {
            String recipe = String.valueOf(projectservice.deleteProject(id));
            return ResponseEntity.status(HttpStatus.OK).body(recipe);
        } catch (Exception e) {
            return null;
        }
    }

    /////TODO DELETE
    @GetMapping("/projectIds")
    public ResponseEntity<List<String>> getProjectId() {
        try {
            List<String> projectIds=projectservice.getProjectIds();
            return ResponseEntity.status(HttpStatus.OK).body(projectIds);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/getProject")
    public ResponseEntity<List<Project>> getBsonDocumentById() {
        try {
            List<Project> projects = projectRepository.findAll();
            if (projects != null) {
                return ResponseEntity.ok(projects);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/getProjectInformation")
    public ResponseEntity<List<Project>> getprojecInformation() {
        try {
            List<Project> projects = projectservice.getProjectsWithOnlyProjectInformation();
            if (projects != null) {
                return ResponseEntity.ok(projects);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }


    @PutMapping(value = "/information/{id}")
    ResponseEntity<Project> saveProjectInformation(@RequestBody String payload, @PathVariable String id) {
        try {
            Project projectinformation = projectservice.createProjectInformation(payload,id);
            return ResponseEntity.status(HttpStatus.OK).body(projectinformation);
        } catch (Exception e) {
            return null;
        }
    }

    @PostMapping(value = "/recipe/{id}")
    ResponseEntity<List<String>> saveRecipe(@PathVariable String id) {
        try {
            List<String> recipe = recipeservice.createRecipe( id);
            return ResponseEntity.status(HttpStatus.OK).body(recipe);
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping(value = "/recipe/{id}")
    ResponseEntity<Optional<Recipe>> getRecipe(@PathVariable String id) {
        try {
            Optional<Recipe> recipe = recipeservice.getRecipe( id);
            return ResponseEntity.status(HttpStatus.OK).body(recipe);
        } catch (Exception e) {
            return null;
        }
    }


    @DeleteMapping("/{idP}/recipe/{idR}")
    public ResponseEntity<String> deleteRecipe(@PathVariable String idP, @PathVariable String idR) {
        try{
            String recipe = recipeservice.DeleteRecipe(idP,idR);
            return ResponseEntity.status(HttpStatus.OK).body(recipe);
        } catch (Exception e) {
            return null;
        }
    }

    @PostMapping(value = "/partialrecipe/{id}")
    ResponseEntity<List<String>> createpartialRecipe(@PathVariable String id) {
        try {
            List<String> recipe = recipeservice.createPartialRecipe(id);
            return ResponseEntity.status(HttpStatus.OK).body(recipe);
        } catch (Exception e) {
            return null;
        }
    }

    @PostMapping(value = "/{id}/recipe/{recipeid}/operation")
    ResponseEntity<List<String>> createOperation(@PathVariable String id, @PathVariable String recipeid) {
        try {
            List<String> recipe = recipeservice.createOperation(id, recipeid);
            return ResponseEntity.status(HttpStatus.OK).body(recipe);
        } catch (Exception e) {
            return null;
        }
    }
    @DeleteMapping(value = "/recipe/operation/{operationId}")
    ResponseEntity<String> deleteOperation( @PathVariable String operationId) {
        try {
            String operation = recipeservice.deleteOperation(operationId);
            return ResponseEntity.status(HttpStatus.OK).body(operation);
        } catch (Exception e) {
            return null;
        }
    }



    @PostMapping(value = "/{id}/operation/{operationId}/phase")
    ResponseEntity<List<String>> createPhase(@PathVariable String id, @PathVariable String operationId) {
        try {
            List<String> recipe = recipeservice.createPhase(id, operationId);
            return ResponseEntity.status(HttpStatus.OK).body(recipe);
        } catch (Exception e) {
            return null;
        }
    }

    @DeleteMapping(value = "/recipe/phase/{phaseId}")
    ResponseEntity<String> deletePhase( @PathVariable String phaseId) {
        try {
            String operation = recipeservice.deletePhase(phaseId);
            return ResponseEntity.status(HttpStatus.OK).body(operation);
        } catch (Exception e) {
            return null;
        }
    }
    @GetMapping("/recipeids")
    public ResponseEntity<List<Recipe>> getRecipeId() {
        try {
            List<Recipe> recipeIds=recipeservice.getRecipeIds();
            return ResponseEntity.status(HttpStatus.OK).body(recipeIds);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }


    @PostMapping(value = "/recipe/description/{id}")
    ResponseEntity<Project> saveDescriptionInformation(@PathVariable String id, @RequestBody String payload) {
        try {
            Project recipedescription = recipeservice.createDescriptionInformation(id, payload);
            return ResponseEntity.status(HttpStatus.OK).body(recipedescription);
        } catch (Exception e) {
            return null;
        }
    }

    @PutMapping(value = "/recipe/parameter/{id}")
    ResponseEntity<Project> saveParameterTable(@PathVariable String id, @RequestBody String payload) {

        try {
            Project parametertable = prametertableService.createTable(id, payload);
            return ResponseEntity.status(HttpStatus.OK).body(parametertable);
        } catch (Exception e) {
            return null;
        }
    }

    @PutMapping(value = "/recipe/signals/{id}")
    ResponseEntity<Project> saveSignals(@PathVariable String id, @RequestBody String payload) {

        try {
            Project signalTable = signalService.createTableSignals(id, payload);
            return ResponseEntity.status(HttpStatus.OK).body(signalTable);
        } catch (Exception e) {
            return null;
        }
    }

    @PutMapping(value = "/recipe/alarmprops/{id}")
    ResponseEntity<Project> saveAlarmProps(@PathVariable String id,@RequestBody String payload) {

        try {
            Project signalTable = alarmPropsService.createTableAlarmProps(id, payload);
            return ResponseEntity.status(HttpStatus.OK).body(signalTable);
        } catch (Exception e) {
            return null;
        }
    }

    @PutMapping(value = "/recipe/logic/{id}")
    ResponseEntity<Project> saveLogic(@PathVariable String id,@RequestBody String payload) {


        try {
            Project logic = logicService.createLogic(id, payload);
            return ResponseEntity.status(HttpStatus.OK).body(logic);
        } catch (Exception e) {
            return null;
        }
    }
//    @PutMapping(value = "/recipe/logic/img/{id}")
//    ResponseEntity<Project> saveImage(@PathVariable String id,@RequestBody String payload) {
//
//
//        try {
//            Project logic = ImageService.saveImg(id, payload);
//            return ResponseEntity.status(HttpStatus.OK).body(logic);
//        } catch (Exception e) {
//            return null;
//        }
//    }

    @PutMapping(value = "/recipe/graph/{id}")
    ResponseEntity<Project> saveGraph(@PathVariable String id,  @RequestBody Map<String, Object> payload) {

        String lines = (String) payload.get("flow");
        try {
            Project logic = graphService.createGraph(id, lines, (String) payload.get("chainArray"));
            return ResponseEntity.status(HttpStatus.OK).body(logic);
        } catch (Exception e) {
            return null;
        }
    }

    @PutMapping(value = "/recipe/stepchain/{id}")
    ResponseEntity<Project> saveStepChain(@PathVariable String id, @RequestBody Map<String, Object> payload) {

        try {
            Project logic = stepChainService.createStepChain(id, (String) payload.get("flow"), (String) payload.get("chainArray"));
            return ResponseEntity.status(HttpStatus.OK).body(logic);
        } catch (Exception e) {
            return null;
        }
    }


    @PutMapping(value = "/recipe/actionsteptable/{id}")
    ResponseEntity<Project> saveActionStepTable(@PathVariable String id, @RequestBody String payload) {

        try {
            Project logic = actionStepTableService.createActionStepTable(id,payload);
            return ResponseEntity.status(HttpStatus.OK).body(logic);
        } catch (Exception e) {
            return null;
        }
    }


}

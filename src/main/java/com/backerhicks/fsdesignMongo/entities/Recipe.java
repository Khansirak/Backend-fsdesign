package com.backerhicks.fsdesignMongo.entities;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Data
@Document(collection = "recipe")
public class Recipe {
    @Id
    private String id;

    private String project_id;

    private String description;

    @DBRef
    private ParameterTable parameterTables;

    @DBRef
    private Signals signalData;

    @DBRef
    private AlarmProps AlarmPropsData;

    @DBRef
    private LogicChain LogicChain;

    @DBRef
    private Graph graphRecipe;

    @DBRef
    private StepChain StepChain;

    @DBRef
    private Set<Recipe> subOperations = new HashSet<>();

    @DBRef
    private Set<Recipe> subPhases = new HashSet<>();


    public void setParameterTables(ParameterTable parameterTable) {
    }
}

package com.backerhicks.fsdesignMongo.entities;

import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.conversions.Bson;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Data
@Document(collection = "stepchain")
public class StepChain {
    @Id
    private String id;
    private String recipe_id;

    private Bson stepChain;


    private String actionStepArray;

    @DBRef
    private Set<ActionStepTable> actionStepTables = new HashSet<>();
}

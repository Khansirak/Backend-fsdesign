package com.backerhicks.fsdesignMongo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.conversions.Bson;
import org.springframework.data.mongodb.core.mapping.Document;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Document(collection = "parameters")

public class ParameterTable {
    @Id
    private String id;
    private String recipe_id;


    private Bson parameterData;
}

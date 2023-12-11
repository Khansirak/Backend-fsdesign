package com.backerhicks.fsdesignMongo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.conversions.Bson;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@Getter
@Setter
@Data
@Document(collection = "signals")
public class Signals {

    @Id
    private String id;
    private String recipe_id;


    private Bson signalData;
}

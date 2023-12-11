package com.backerhicks.fsdesignMongo.entities;

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
@Document(collection = "logic")
public class LogicChain {
    @Id
    private String id;
    private String recipe_id;


    private Bson logic;

//    private Bson logicchain;
    
}

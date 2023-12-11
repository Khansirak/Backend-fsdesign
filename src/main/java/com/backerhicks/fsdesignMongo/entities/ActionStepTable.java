package com.backerhicks.fsdesignMongo.entities;

import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.conversions.Bson;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@Getter
@Setter
@Data
@Document(collection = "actionsteptable")
public class ActionStepTable {
    @Id
    @Indexed(unique = true)
    private String id;
    private String recipe_id;
    private String stepchain_id;

    private Bson actionstepTable;

}

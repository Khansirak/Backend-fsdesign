package com.backerhicks.fsdesignMongo.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.conversions.Bson;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@NoArgsConstructor
@Getter
@Setter
@Data
@Document(collection = "project")
public class Project {
    @Id

    private String id;
    private Bson projectInformation;

    @DBRef
    private Set<Recipe> recipe = new HashSet<>();

    @DBRef
    private Set<Recipe> partialRecipe = new HashSet<>();


}

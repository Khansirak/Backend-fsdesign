package com.backerhicks.fsdesignMongo.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.conversions.Bson;

@NoArgsConstructor
@Getter
@Setter
@Data
public class StepChainPayload {
    private String flow;
    private String myArray;
}

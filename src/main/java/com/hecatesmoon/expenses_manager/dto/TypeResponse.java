package com.hecatesmoon.expenses_manager.dto;

import com.hecatesmoon.expenses_manager.model.DebtType;

public class TypeResponse {
    private DebtType value;
    private String description;

    public static TypeResponse from (DebtType type){
        TypeResponse typeResponse = new TypeResponse();
        typeResponse.value = type;
        typeResponse.description = type.getDescription();
        return typeResponse;
    }

    public DebtType getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    
}

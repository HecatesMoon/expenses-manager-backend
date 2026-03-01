package com.hecatesmoon.expenses_manager.dto;

import java.util.List;

import com.hecatesmoon.expenses_manager.model.DebtEntry;
import com.hecatesmoon.expenses_manager.model.User;

public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private List<DebtEntry> debtEntries;

    public static UserResponse from (User user){
        UserResponse response = new UserResponse();
        response.id = user.getId();
        response.firstName = user.getFirstName();
        response.lastName = user.getLastName();
        response.debtEntries = user.getDebtEntries();
        return response;
    }
}

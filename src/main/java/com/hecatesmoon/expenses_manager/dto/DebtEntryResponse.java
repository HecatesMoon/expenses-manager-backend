package com.hecatesmoon.expenses_manager.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.hecatesmoon.expenses_manager.model.DebtEntry;
import com.hecatesmoon.expenses_manager.model.DebtType;
import com.hecatesmoon.expenses_manager.model.User;

public class DebtEntryResponse {

    private Long id;
    private BigDecimal moneyAmount;
    private DebtType type;
    private Boolean isPaid;
    private Boolean isActive;
    private LocalDateTime dateLimit;
    private User user;

    public static DebtEntryResponse from (DebtEntry entry){
        DebtEntryResponse response = new DebtEntryResponse();
        response.id = entry.getId();
        response.moneyAmount = entry.getMoneyAmount();
        response.type = entry.getType();
        response.isPaid = entry.getIsPaid();
        response.isActive = entry.getIsActive();
        response.dateLimit = entry.getDateLimit();
        response.user = entry.getUser(); //todo: use userdto
        return response;
    }

}

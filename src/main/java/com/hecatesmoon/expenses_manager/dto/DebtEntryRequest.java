package com.hecatesmoon.expenses_manager.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.hecatesmoon.expenses_manager.model.DebtEntry;
import com.hecatesmoon.expenses_manager.model.DebtType;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class DebtEntryRequest {

    @NotNull(message = "Amount can't be null")
    @Positive(message = "Amount has to be greater than zero")
    private BigDecimal moneyAmount;

    @NotNull(message = "You have to choose a type")
    private DebtType type;

    private Boolean isPaid = false;

    private Boolean isActive = true;

    @Future(message = "The date has to be in the future")
    private LocalDateTime dateLimit;

    public static DebtEntry from (DebtEntryRequest request){
        DebtEntry entry = new DebtEntry();
        entry.setMoneyAmount(request.moneyAmount);
        entry.setType(request.type);
        entry.setIsPaid(request.isPaid);
        entry.setIsActive(request.isActive);
        entry.setDateLimit(request.dateLimit);
        return entry;
    }
}

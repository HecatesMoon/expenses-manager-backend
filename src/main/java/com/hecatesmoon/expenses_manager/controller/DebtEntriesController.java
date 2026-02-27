package com.hecatesmoon.expenses_manager.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.hecatesmoon.expenses_manager.exception.UnauthorizedException;
import com.hecatesmoon.expenses_manager.model.DebtEntry;
import com.hecatesmoon.expenses_manager.model.DebtType;
import com.hecatesmoon.expenses_manager.service.DebtEntriesService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class DebtEntriesController {
    
    @Autowired
    private final DebtEntriesService debtService;

    public DebtEntriesController (DebtEntriesService debtService){
        this.debtService = debtService;
    }

    //User based endpoints
    @GetMapping("/api/debt/entries/all")
    public List<DebtEntry> getAllEntries(HttpSession session) {
        Long userId = (Long) session.getAttribute("user_id");
        if (userId == null) throw new UnauthorizedException("You need to login first");

        return this.debtService.getAllUserEntries(userId);
    }

    @PostMapping("/api/debt/entries/create")
    public ResponseEntity<DebtEntry> addDebtEntry(@Valid @RequestBody DebtEntry debtEntry, HttpSession session) {
        Long userId = (Long) session.getAttribute("user_id");
        if (userId == null) throw new UnauthorizedException("You need to login first");
        
        DebtEntry saved = this.debtService.saveEntry(debtEntry, userId);
        return ResponseEntity.ok(saved);
    }
    
    @GetMapping("/api/debt/entries/get/{id}")
    public ResponseEntity<DebtEntry> getEntryById(@PathVariable Long id, HttpSession session) {
        Long userId = (Long) session.getAttribute("user_id");
        if (userId == null) throw new UnauthorizedException("You need to login first");

        DebtEntry entry = this.debtService.getById(id, userId);

        return ResponseEntity.ok(entry);
    }

    @DeleteMapping("/api/debt/entries/delete/{id}")
    public ResponseEntity<Void> deleteEntryById(@PathVariable Long id, HttpSession session) {
        Long userId = (Long) session.getAttribute("user_id");
        if (userId == null) throw new UnauthorizedException("You need to login first");

        this.debtService.deleteEntry(id, userId);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/api/debt/entries/update/{id}")
    public ResponseEntity<DebtEntry> updateEntry(@Valid @RequestBody DebtEntry debtEntry, @PathVariable Long id, HttpSession session) {
        Long userId = (Long) session.getAttribute("user_id");
        if (userId == null) throw new UnauthorizedException("You need to login first");

        DebtEntry updated = debtService.updateEntry(debtEntry, id, userId);

        return ResponseEntity.ok(updated);
    }
    
    @GetMapping("/api/debt/total")
    public ResponseEntity<BigDecimal> getTotalAmount(HttpSession session) {
        Long userId = (Long) session.getAttribute("user_id");
        if (userId == null) throw new UnauthorizedException("You need to login first");

        //todo: better json as response
        return ResponseEntity.ok(debtService.getTotalDebt(userId));
    }

    //general endpoints

    @GetMapping("/api/types/list")
    public ResponseEntity<DebtType[]> getTypeList() {
        return ResponseEntity.ok(DebtType.values());
    }
}
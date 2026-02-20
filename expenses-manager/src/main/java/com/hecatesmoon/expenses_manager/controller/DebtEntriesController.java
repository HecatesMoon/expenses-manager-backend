package com.hecatesmoon.expenses_manager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.hecatesmoon.expenses_manager.model.DebtEntry;
import com.hecatesmoon.expenses_manager.service.DebtEntriesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
public class DebtEntriesController {
    
    @Autowired
    private final DebtEntriesService debtEntriesService;

    public DebtEntriesController (DebtEntriesService debtEntriesService){
        this.debtEntriesService = debtEntriesService;
    }

    @GetMapping("/api/debt/entries/all")
    public List<DebtEntry> getAllEntries() {
        return this.debtEntriesService.getAll();
    }

    @PostMapping("/api/debt/entry/new")
    public ResponseEntity<DebtEntry> postMethodName(@RequestBody DebtEntry debtEntry) {
        DebtEntry saved = this.debtEntriesService.saveEntry(debtEntry);
        return ResponseEntity.ok(saved);
    }
    
    
}

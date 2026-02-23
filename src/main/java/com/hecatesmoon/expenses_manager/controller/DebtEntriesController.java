package com.hecatesmoon.expenses_manager.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.hecatesmoon.expenses_manager.model.DebtEntry;
import com.hecatesmoon.expenses_manager.model.DebtType;
import com.hecatesmoon.expenses_manager.service.DebtEntriesService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @PostMapping("/api/debt/entries/create")
    public ResponseEntity<DebtEntry> addDebtEntry(@Valid @RequestBody DebtEntry debtEntry) {
        DebtEntry saved = this.debtEntriesService.saveEntry(debtEntry);
        return ResponseEntity.ok(saved);
    }
    
    @GetMapping("/api/debt/entries/get/{id}")
    public ResponseEntity<DebtEntry> getEntryById(@PathVariable Long id) {
        DebtEntry entry = this.debtEntriesService.getById(id);
        return ResponseEntity.ok(entry);
    }

    @DeleteMapping("/api/debt/entries/delete/{id}")
    public ResponseEntity<Void> deleteEntryById(@PathVariable Long id) {
        this.debtEntriesService.deleteEntry(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/api/debt/entries/update/{id}")
    public ResponseEntity<DebtEntry> updateEntry(@Valid @RequestBody DebtEntry debtEntry, @PathVariable Long id) {
        
        DebtEntry updated = debtEntry;
        updated.setId(id);
        updated = debtEntriesService.updateEntry(updated);

        return ResponseEntity.ok(updated);
    }
    
    @GetMapping("/api/types/list")
    public ResponseEntity<DebtType[]> getTypeList() {
        return ResponseEntity.ok(DebtType.values());
    }

    @GetMapping("/api/debt/total")
    public ResponseEntity<BigDecimal> getTotalAmount() {
        return ResponseEntity.ok(debtEntriesService.getTotalDebt());
    }
    
    
    
}
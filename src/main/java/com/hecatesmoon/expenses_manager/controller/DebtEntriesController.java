package com.hecatesmoon.expenses_manager.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.hecatesmoon.expenses_manager.exception.UnauthorizedException;
import com.hecatesmoon.expenses_manager.model.DebtEntry;
import com.hecatesmoon.expenses_manager.model.DebtType;
import com.hecatesmoon.expenses_manager.model.User;
import com.hecatesmoon.expenses_manager.service.DebtEntriesService;
import com.hecatesmoon.expenses_manager.service.UsersService;

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
    @Autowired
    private final UsersService usersService;

    public DebtEntriesController (DebtEntriesService debtService, UsersService usersService){
        this.debtService = debtService;
        this.usersService = usersService;
    }

    //User based endpoints
    @GetMapping("/api/debt/entries/all")
    public List<DebtEntry> getAllEntries(HttpSession session) {
        if (session.getAttribute("user_id") == null) throw new UnauthorizedException("You need to login first");
        Long userId = (Long) session.getAttribute("user_id");
        return this.debtService.getAllUserEntries(userId);
    }

    @PostMapping("/api/debt/entries/create")
    public ResponseEntity<DebtEntry> addDebtEntry(@Valid @RequestBody DebtEntry debtEntry, HttpSession session) {
        if (session.getAttribute("user_id") == null) throw new UnauthorizedException("You need to login first");
        
        User user = this.usersService.getUserById((Long) session.getAttribute("user_id"));
        debtEntry.setUser(user);

        DebtEntry saved = this.debtService.saveEntry(debtEntry);
        return ResponseEntity.ok(saved);
    }
    
    @GetMapping("/api/debt/entries/get/{id}")
    public ResponseEntity<DebtEntry> getEntryById(@PathVariable Long id, HttpSession session) {
        if (session.getAttribute("user_id") == null) throw new UnauthorizedException("You need to login first");

        DebtEntry entry = this.debtService.getById(id);

        //todo: use an exception in service
        if (entry.getUser().getId() != session.getAttribute("user_id")){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return ResponseEntity.ok(entry);
    }

    @DeleteMapping("/api/debt/entries/delete/{id}")
    public ResponseEntity<Void> deleteEntryById(@PathVariable Long id, HttpSession session) {
        if (session.getAttribute("user_id") == null) throw new UnauthorizedException("You need to login first");

        //todo: apply DRY
        DebtEntry entry = this.debtService.getById(id);

        //todo: use an exception in service
        if (entry.getUser().getId() != session.getAttribute("user_id")){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        this.debtService.deleteEntry(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/api/debt/entries/update/{id}")
    public ResponseEntity<DebtEntry> updateEntry(@Valid @RequestBody DebtEntry debtEntry, @PathVariable Long id, HttpSession session) {
        if (session.getAttribute("user_id") == null) throw new UnauthorizedException("You need to login first");

        //todo: apply DRY
        DebtEntry entry = this.debtService.getById(id);

        //todo: use an exception in service
        if (entry.getUser().getId() != session.getAttribute("user_id")){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        
        DebtEntry updated = debtEntry;
        updated.setId(id);
        updated = debtService.updateEntry(updated);

        return ResponseEntity.ok(updated);
    }
    
    @GetMapping("/api/debt/total")
    public ResponseEntity<BigDecimal> getTotalAmount(HttpSession session) {
        if (session.getAttribute("user_id") == null) throw new UnauthorizedException("You need to login first");

        //todo: better json as response
        return ResponseEntity.ok(debtService.getTotalDebt((Long) session.getAttribute("user_id")));
    }

    //general endpoints

    @GetMapping("/api/types/list")
    public ResponseEntity<DebtType[]> getTypeList() {
        return ResponseEntity.ok(DebtType.values());
    }
}
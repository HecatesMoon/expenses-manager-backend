package com.hecatesmoon.expenses_manager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.hecatesmoon.expenses_manager.model.DebtEntry;
import com.hecatesmoon.expenses_manager.repository.DebtEntriesRepository;

@Service
public class DebtEntriesService {

    @Autowired
    private final DebtEntriesRepository debtEntriesRepository;

    public DebtEntriesService(DebtEntriesRepository debtEntriesRepository){
        this.debtEntriesRepository = debtEntriesRepository;
    }

    public List<DebtEntry> getAll(){
        return this.debtEntriesRepository.findAll();
    }

    public DebtEntry getById(Long id){
        return this.debtEntriesRepository.findById(id)
                   .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entry not found by id: " + id));
    }

    public DebtEntry saveEntry(DebtEntry debtEntry) {
        return this.debtEntriesRepository.save(debtEntry);
    }

    public void deleteEntry(Long id) {
        if (!this.debtEntriesRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entry not found by id: " + id);
        }

        this.debtEntriesRepository.deleteById(id);
    }

    public void deleteEntry(DebtEntry entry) {
        Long id = entry.getId();

        if (!this.debtEntriesRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entry not found by id: " + id);
        }

        this.debtEntriesRepository.deleteById(id);
    }
    
}

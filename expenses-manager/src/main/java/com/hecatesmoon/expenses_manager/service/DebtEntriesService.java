package com.hecatesmoon.expenses_manager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public DebtEntry saveEntry(DebtEntry debtEntry) {
        return this.debtEntriesRepository.save(debtEntry);
    }
    
}

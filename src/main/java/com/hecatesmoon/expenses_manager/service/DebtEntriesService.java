package com.hecatesmoon.expenses_manager.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.hecatesmoon.expenses_manager.exception.AccessDeniedException;
import com.hecatesmoon.expenses_manager.exception.ResourceNotFoundException;
import com.hecatesmoon.expenses_manager.model.DebtEntry;
import com.hecatesmoon.expenses_manager.repository.DebtEntriesRepository;
import com.hecatesmoon.expenses_manager.repository.UsersRepository;

@Service
public class DebtEntriesService {

    @Autowired
    private final DebtEntriesRepository debtRepository;
    @Autowired
    private final UsersRepository usersRepository;

    public DebtEntriesService(DebtEntriesRepository debtRepository, UsersRepository usersRepository){
        this.debtRepository = debtRepository;
        this.usersRepository = usersRepository;
    }

    public List<DebtEntry> getAll(){
        return this.debtRepository.findAll();
    }

    public List<DebtEntry> getAllUserEntries(Long id){

        if (!usersRepository.existsById(id)){
            throw new AccessDeniedException("This user does not exist: " + id);
        }

        return this.debtRepository.findByUserId(id);
    }
    
    //todo: manage null or use exception

    public DebtEntry getById(Long id, Long userId){
        //todo: consider make a standard method for exception
        DebtEntry entry = this.debtRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("This debt entry does not exist: " + id));

        if (entry.getUser().getId() != userId){
            throw new AccessDeniedException("You do not have access to this entry.");
        }

        return entry;
    }

    public DebtEntry saveEntry(DebtEntry debtEntry, Long userId) {
        debtEntry.setUser(this.usersRepository.findById(userId)
                                              .orElseThrow(() -> new ResourceNotFoundException("User does not exist: " + userId)));
        return this.debtRepository.save(debtEntry);
    }

    public DebtEntry updateEntry(DebtEntry debtEntry, Long id, Long userId) {
        DebtEntry original = debtRepository.findById(id)
                                           .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entry not found by id: " + id));

        if (original.getUser().getId() != userId){
            throw new AccessDeniedException("You do not have access to this entry.");
        }

        debtEntry.setId(id);
        debtEntry.setCreatedAt(original.getCreatedAt());

        return this.debtRepository.save(debtEntry);
    }

    public void deleteEntry(Long id, Long userId) {
        //todo: apply DRY
        DebtEntry entry = this.debtRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("This debt entry does not exist: " + id));

        if (entry.getUser().getId() != userId){
            throw new AccessDeniedException("You do not have access to this entry.");
        }

        this.debtRepository.deleteById(id);
    }

    public void deleteEntry(DebtEntry entry, Long userId) {
        this.deleteEntry(entry.getId(), userId);
    }

    //todo: sum from sql
    public BigDecimal getTotalDebt(Long id){
        BigDecimal total = BigDecimal.ZERO;
        List<DebtEntry> debtList = debtRepository.findByUserId(id);
        for (DebtEntry entry : debtList){
            total = total.add(entry.getMoneyAmount());
        }

        return total;
    }
    
}
package com.hecatesmoon.expenses_manager.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hecatesmoon.expenses_manager.model.DebtEntry;

@Repository
public interface DebtEntriesRepository extends JpaRepository<DebtEntry, Long>{
    List<DebtEntry> findAll();

    DebtEntry save(DebtEntry debtEntry);

    Optional<DebtEntry> findById(Long id);

    void deleteById(Long id);

    List<DebtEntry> findByIsActiveTrue();

}
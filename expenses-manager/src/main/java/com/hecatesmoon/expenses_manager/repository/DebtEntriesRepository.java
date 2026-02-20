package com.hecatesmoon.expenses_manager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hecatesmoon.expenses_manager.model.DebtEntry;

@Repository
public interface DebtEntriesRepository extends JpaRepository<DebtEntry, Long>{
    List<DebtEntry> findAll();

    DebtEntry save(DebtEntry debtEntry);

}

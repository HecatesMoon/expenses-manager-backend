package com.hecatesmoon.expenses_manager.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.hecatesmoon.expenses_manager.model.DebtEntry;
import com.hecatesmoon.expenses_manager.repository.DebtEntriesRepository;
import com.hecatesmoon.expenses_manager.repository.UsersRepository;

import io.jsonwebtoken.lang.Collections;

public class DebtEntriesServiceTest {
    
    private final DebtEntriesRepository debtEntriesRepoMock = mock(DebtEntriesRepository.class);
    private final UsersRepository usersRepoMock = mock(UsersRepository.class);

    private DebtEntriesService debtEntriesService = new DebtEntriesService(debtEntriesRepoMock, usersRepoMock);
    
    @Test
    public void getAll_SimpleList(){
        DebtEntry entry1 = new DebtEntry(); 
        DebtEntry entry2 = new DebtEntry(); 
        DebtEntry entry3 = new DebtEntry();
        List<DebtEntry> list = List.of(entry1,entry2,entry3);

        when(debtEntriesRepoMock.findAll()).thenReturn(list);

        List<DebtEntry> result = debtEntriesService.getAll();

        Assertions.assertIterableEquals(list, result);
    }
    
    @Test
    public void getAll_EmptyList(){
        List<DebtEntry> list = Collections.emptyList();

        when(debtEntriesService.getAll()).thenReturn(list);

        List<DebtEntry> result = debtEntriesService.getAll();

        Assertions.assertIterableEquals(list, result);
    }
    
}

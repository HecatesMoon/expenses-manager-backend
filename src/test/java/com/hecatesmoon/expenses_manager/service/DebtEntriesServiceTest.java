package com.hecatesmoon.expenses_manager.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.hecatesmoon.expenses_manager.dto.DebtEntryResponse;
import com.hecatesmoon.expenses_manager.exception.ResourceNotFoundException;
import com.hecatesmoon.expenses_manager.model.DebtEntry;
import com.hecatesmoon.expenses_manager.model.User;
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

    @Test
    public void getById_invalidId(){
        long id = 12l;
        long userId = 6l;
        when(debtEntriesRepoMock.findById(id)).thenThrow(new ResourceNotFoundException("entry not found, id: " + id));

        Assertions.assertThrows(ResourceNotFoundException.class, ()->{
            debtEntriesService.getById(id, userId);
        });

        //todo: verify methods are not called
    }

    @Test
    public void getById_validIdAndUserIsOwner(){
        long id = 12l;
        long userId = 4l;
        DebtEntry entry = new DebtEntry();
        entry.setId(id);
        User user = new User();
        user.setId(userId);
        entry.setUser(user);

        when(debtEntriesRepoMock.findById(id)).thenReturn(Optional.of(entry));

        DebtEntryResponse expected = DebtEntryResponse.from(entry);
        DebtEntryResponse result = debtEntriesService.getById(id, userId);

        //todo: make isequal method for dto
        Assertions.assertEquals(expected.getId(), result.getId());

    }
    
}

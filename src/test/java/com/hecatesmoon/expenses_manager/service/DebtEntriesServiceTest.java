package com.hecatesmoon.expenses_manager.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.hecatesmoon.expenses_manager.dto.DebtEntryResponse;
import com.hecatesmoon.expenses_manager.exception.AccessDeniedException;
import com.hecatesmoon.expenses_manager.exception.BusinessException;
import com.hecatesmoon.expenses_manager.exception.ResourceNotFoundException;
import com.hecatesmoon.expenses_manager.model.DebtEntry;
import com.hecatesmoon.expenses_manager.model.DebtType;
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
    public void getAllUserEntries_PageSizeAboveLimit(){
        long userId = 1l;
        Pageable pageable = PageRequest.of(0, 51, Sort.by("createdAt").descending());

        Assertions.assertThrows(BusinessException.class, () -> {
            debtEntriesService.getAllUserEntries(userId, null, null, pageable);
        });

        verify(debtEntriesRepoMock, never()).findByUserIdWithFilters(anyLong(), any(), any(), any(Pageable.class));

    }

    @Test
    public void getAllUserEntries_ValidPageable(){
        long userId = 1l;
        Pageable pageable = PageRequest.of(0, 1, Sort.by("createdAt").descending());

        DebtEntry entry = new DebtEntry();
        entry.setId(1l);
        entry.setMoneyAmount(BigDecimal.valueOf(2000));
        User user = new User();
        entry.setUser(user);
        Page<DebtEntry> page = new PageImpl<>(List.of(entry));

        when(debtEntriesRepoMock.findByUserIdWithFilters(userId, true, false, pageable)).thenReturn(page);

        Page<DebtEntryResponse> result = debtEntriesService.getAllUserEntries(userId, true, false, pageable);

        DebtEntryResponse responseItem = result.getContent().get(0);

        Assertions.assertEquals(responseItem.getId(), entry.getId());
        Assertions.assertEquals(responseItem.getMoneyAmount(), entry.getMoneyAmount());
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

    @Test public void getById_validIdButUserIsNotOwner(){
        long userId = 14l;
        User user = new User();
        user.setId(userId);
        long userId2 = 3l;
        User user2 = new User();
        user2.setId(userId2);

        long id = 12l;
        DebtEntry entry = new DebtEntry();
        entry.setId(id);
        entry.setUser(user);

        when(debtEntriesRepoMock.findById(id)).thenReturn(Optional.of(entry));

        Assertions.assertThrows(AccessDeniedException.class, () -> {
            debtEntriesService.getById(id, userId2);
        } );
    }

    @Test public void debtEntryResponse_From_FullObject(){
        DebtEntry entry = new DebtEntry();
        entry.setId(1l);
        entry.setMoneyAmount(BigDecimal.valueOf(5000.5));
        entry.setIsPaid(false);
        entry.setIsActive(true);
        entry.setCreditor("aunt silvia");
        entry.setDescription("she lend me money");
        entry.setType(DebtType.FAMILY);
        entry.setDateLimit(LocalDateTime.of(2027, 2, 3, 4, 42));
        entry.setCreatedAt(LocalDateTime.of(2026, 9, 1, 8, 34));
        entry.setUpdatedAt(LocalDateTime.of(2026, 9, 1, 8, 37));
        User user = new User();
        user.setId(4l);
        user.setFirstName("alex");
        user.setLastName("turner");
        user.setCreatedAt(LocalDateTime.of(2025, 10, 2, 13, 22));
        user.setUpdatedAt(LocalDateTime.of(2025, 10, 2, 13, 30));
        user.setDebtEntries(List.of(entry));
        user.setEmail("epic@mail.com");
        user.setPassword("epicpassword");
        entry.setUser(user);

        DebtEntryResponse result = DebtEntryResponse.from(entry);

        Assertions.assertEquals(1l, result.getId());
        Assertions.assertEquals("she lend me money", result.getDescription());
        Assertions.assertEquals(BigDecimal.valueOf(5000.5), result.getMoneyAmount());
        Assertions.assertEquals("aunt silvia", result.getCreditor());
        Assertions.assertEquals(DebtType.FAMILY, result.getType());
        Assertions.assertFalse(result.getIsPaid());
        Assertions.assertTrue(result.getIsActive());
        Assertions.assertEquals(LocalDateTime.of(2027, 2, 3, 4, 42), result.getDateLimit());

        Assertions.assertEquals(4l, result.getUser().getId());
        Assertions.assertEquals("epic@mail.com", result.getUser().getEmail());
        Assertions.assertEquals("alex", result.getUser().getFirstName());
        Assertions.assertEquals("turner", result.getUser().getLastName());

    }
    
}

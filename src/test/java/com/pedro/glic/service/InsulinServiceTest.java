package com.pedro.glic.service;

import com.pedro.glic.dto.InsulinRequestDTO;
import com.pedro.glic.dto.InsulinResponseDTO;
import com.pedro.glic.entity.Insulin;
import com.pedro.glic.entity.User;
import com.pedro.glic.enums.DiabetesType;
import com.pedro.glic.enums.InsulinFormat;
import com.pedro.glic.enums.InsulinType;
import com.pedro.glic.repository.InsulinRepository;
import com.pedro.glic.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class InsulinServiceTest {

    @Mock
    private InsulinRepository insulinRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private InsulinService insulinService;


    private User user;

    private Insulin insulin;

    private InsulinRequestDTO dto;

    @BeforeEach
    void setUp(){
        user = new User("Pedro", "pedro@email.com", "senhaCriptografada",
                LocalDate.of(2004, 3, 19), LocalDate.of(2025, 8, 1),
                "31975877180", 15, DiabetesType.TYPE_1);
        insulin = new Insulin("Lantus", "tomar de manha", 200, new BigDecimal("100.00"), 1, LocalDate.now(), LocalDate.now(), InsulinType.LongActing, InsulinFormat.Pens);
        insulin.setUser(user);

        dto = new InsulinRequestDTO("Lantus", 200, new BigDecimal("100.00"), 1,
                LocalDate.now(), LocalDate.now(), "tomar de manha",
                InsulinType.LongActing, InsulinFormat.Pens);
    }

    @Test
    void shouldCreateInsulinSucessfully() {
        Long id = 1L;

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(insulinRepository.save(any(Insulin.class))).thenReturn(insulin);

        InsulinResponseDTO result = insulinService.createInsulin(id, dto);

        assertNotNull(result);
        assertEquals("Lantus", result.name());
        assertEquals(InsulinType.LongActing, result.type());

        verify(userRepository).findById(id);
        verify(insulinRepository).save(any(Insulin.class));
    }

    @Test
    void shouldThrowExceptionWhenUserNotFoundOnCreate() {
        Long nonExistingId = 99L;

        when(userRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            insulinService.createInsulin(nonExistingId, dto);
        });

        verify(userRepository).findById(nonExistingId);
        verify(insulinRepository, never()).save(any(Insulin.class));
    }

    @Test
    void shouldThrowExceptionWhenInsulinDoesNotBelongToUser() {
        Long userId = 1L;
        Long insulinId = 1L;

        User otherUser = new User("Outro", "outro@gmail.com", "123456", LocalDate.of(2000, 1, 1), LocalDate.now(), "1199999999", 10, DiabetesType.TYPE_2);

        insulin.setUser(otherUser);

        when(insulinRepository.findById(insulinId)).thenReturn(Optional.of(insulin));

        assertThrows(RuntimeException.class, () -> {
            insulinService.findById(userId, insulinId);
        });

        verify(insulinRepository).findById(insulinId);
    }

}

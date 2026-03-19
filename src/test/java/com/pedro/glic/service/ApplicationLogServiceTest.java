package com.pedro.glic.service;

import com.pedro.glic.dto.ApplicationLogRequestDTO;
import com.pedro.glic.dto.ApplicationLogResponseDTO;
import com.pedro.glic.entity.ApplicationLog;
import com.pedro.glic.entity.Insulin;
import com.pedro.glic.entity.User;
import com.pedro.glic.enums.DiabetesType;
import com.pedro.glic.enums.InsulinFormat;
import com.pedro.glic.enums.InsulinType;
import com.pedro.glic.repository.ApplicationLogRepository;
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
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ApplicationLogServiceTest {

    @Mock
    private ApplicationLogRepository applicationLogRepository;

    @Mock
    private InsulinRepository insulinRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ApplicationLogService applicationLogService;

    private User user;

    private Insulin insulin;

    private ApplicationLog applicationLog;

    private ApplicationLogRequestDTO dto;

    @BeforeEach
    public void setUp() throws Exception {
        user = new User("Pedro", "pedro@email.com", "senhaCriptografada",
                LocalDate.of(2004, 3, 19), LocalDate.of(2025, 8, 1),
                "31975877180", 15, DiabetesType.TYPE_1);
        setId(user, 1L);

        insulin = new Insulin("Fiasp", "tomar antes de comer", 200,
                new BigDecimal("100.00"), 1, LocalDate.now(), LocalDate.now(),
                InsulinType.Rapid, InsulinFormat.Pens);
        insulin.setUser(user);
        setId(insulin, 1L);

        applicationLog = new ApplicationLog(2, LocalDateTime.now(), 35.0, user, insulin);
        setId(applicationLog, 1L);

        dto = new ApplicationLogRequestDTO(2, LocalDateTime.now(), 35.0,
                insulin.getId(), user.getId());
    }

    @Test
    void shouldCreateApplicationLogSuccessfully() {
        Long id = 1L;

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(insulinRepository.findById(id)).thenReturn(Optional.of(insulin));
        when(applicationLogRepository.save(any(ApplicationLog.class))).thenReturn(applicationLog);

        ApplicationLogResponseDTO result = applicationLogService.createApplication(id, id, dto);

        assertNotNull(result);
        assertEquals(2, result.appliedUnits());
        assertEquals("Fiasp", result.insulin().name());

        verify(userRepository).findById(1L);
        verify(insulinRepository).findById(1L);
        verify(applicationLogRepository).save(any(ApplicationLog.class));
    }

    @Test
    void shouldThrowExceptionWhenUserNotFoundOnCreateLog(){
        Long nonExistingId = 1L;

        when(userRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            applicationLogService.createApplication(nonExistingId, nonExistingId, dto);
        });

        verify(userRepository).findById(nonExistingId);
        verify(insulinRepository, never()).findById(any());
        verify(applicationLogRepository, never()).save(any(ApplicationLog.class));
    }

    @Test
    void shouldThrowExceptionWhenInsulinNotFoundOnCreateLog(){
        Long userId = 1L;
        Long insulinId = 99L;

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(insulinRepository.findById(insulinId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            applicationLogService.createApplication(userId, insulinId, dto);
        });

        verify(userRepository).findById(userId);
        verify(insulinRepository).findById(insulinId);
        verify(applicationLogRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenInsulinDoesNotBelongToUserOnCreateLog(){
        Long userId = 1L;
        Long insulinId = 1L;

        User otherUser = new User("Outro", "outro@gmail.com", "123456", LocalDate.of(2000, 1, 1), LocalDate.now(), "1199999999", 10, DiabetesType.TYPE_2);

        insulin.setUser(otherUser);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(insulinRepository.findById(insulinId)).thenReturn(Optional.of(insulin));

        assertThrows(RuntimeException.class, () -> {
            applicationLogService.createApplication(userId, insulinId, dto);
        });

        verify(userRepository).findById(userId);
        verify(insulinRepository).findById(insulinId);
        verify(applicationLogRepository, never()).save(any());
    }

    private void setId(Object entity, Long id) throws Exception {
        java.lang.reflect.Field field = entity.getClass().getDeclaredField("id");
        field.setAccessible(true);
        field.set(entity, id);
    }

}

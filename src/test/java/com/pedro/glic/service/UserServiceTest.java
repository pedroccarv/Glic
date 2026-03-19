package com.pedro.glic.service;

import com.pedro.glic.dto.UserRequestDTO;
import com.pedro.glic.dto.UserResponseDTO;
import com.pedro.glic.entity.User;
import com.pedro.glic.enums.DiabetesType;
import com.pedro.glic.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private UserRequestDTO dto;

    private User user;

    @BeforeEach
    void setUp() {
        dto = new UserRequestDTO("Pedro", "pedro@email.com", "123456",
                LocalDate.of(2004, 3, 19), LocalDate.of(2025, 8, 1),
                "31975877180", 15, DiabetesType.TYPE_1);
        user = new User("Pedro", "pedro@email.com", "senhaCriptografada",
                LocalDate.of(2004, 3, 19), LocalDate.of(2025, 8, 1),
                "31975877180", 15, DiabetesType.TYPE_1);
    }

    @Test
    void shouldCreateUserSucessfully() {

        when(userRepository.existsByEmail(dto.email())).thenReturn(false);
        when(passwordEncoder.encode(dto.password())).thenReturn("senhaCriptografada");
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserResponseDTO result = userService.createUser(dto);

        assertNotNull(result);
        assertEquals("Pedro", result.name());
        assertEquals("pedro@email.com", result.email());

        verify(userRepository).existsByEmail(dto.email());
        verify(passwordEncoder).encode(dto.password());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {

        when(userRepository.existsByEmail(dto.email())).thenReturn(true);

        assertThrows(RuntimeException.class, () -> {
            userService.createUser(dto);
        });
        verify(userRepository).existsByEmail(dto.email());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void shouldFindUserById(){
        Long id = 1L;

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        UserResponseDTO result = userService.findById(id);

        assertNotNull(result);
        assertEquals("Pedro", result.name());
        assertEquals("pedro@email.com", result.email());

        verify(userRepository).findById(id);

    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        Long nonExistingId = 99L;

        when(userRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            userService.findById(nonExistingId);
        });

        verify(userRepository).findById(nonExistingId);
    }
    
}

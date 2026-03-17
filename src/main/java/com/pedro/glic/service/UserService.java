package com.pedro.glic.service;

import com.pedro.glic.dto.UserRequestDTO;
import com.pedro.glic.dto.UserResponseDTO;
import com.pedro.glic.entity.User;
import com.pedro.glic.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserResponseDTO> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream().map(UserResponseDTO::toDTO).toList();
    }

    public UserResponseDTO findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return UserResponseDTO.toDTO(user.orElseThrow(() -> new RuntimeException("User not found")));
    }

    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        if (userRepository.existsByEmail(userRequestDTO.email())){
            throw new RuntimeException("O e-mail ja existe");
        }
        User user = new User(
                userRequestDTO.name(),
                userRequestDTO.email(),
                passwordEncoder.encode(userRequestDTO.password()),
                userRequestDTO.birthday(),
                userRequestDTO.diagnostic(),
                userRequestDTO.phone(),
                userRequestDTO.carbCounting(),
                userRequestDTO.diabetesType()
        );
        User userSaved = userRepository.save(user);
        return UserResponseDTO.toDTO(userSaved);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }

    public UserResponseDTO updateUser(Long id, UserRequestDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.updateFrom(dto);
        return UserResponseDTO.toDTO(userRepository.save(user));
    }

}

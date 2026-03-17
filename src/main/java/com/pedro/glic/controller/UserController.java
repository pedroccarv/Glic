package com.pedro.glic.controller;

import com.pedro.glic.dto.UserRequestDTO;
import com.pedro.glic.dto.UserResponseDTO;
import com.pedro.glic.entity.User;
import com.pedro.glic.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> dto = userService.findAll();
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getMe(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok().body(userService.findById(user.getId()));
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO dto) {
       UserResponseDTO newUser = userService.createUser(dto);
       return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @DeleteMapping("/me")
    public ResponseEntity<UserResponseDTO> deleteUser(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        userService.deleteUser(user.getId());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/me")
    public ResponseEntity<UserResponseDTO> updateUser(Authentication authentication, @Valid @RequestBody UserRequestDTO dto) {
        User user = (User) authentication.getPrincipal();
        UserResponseDTO newUser = userService.updateUser(user.getId(), dto);
        return ResponseEntity.ok().body(newUser);
    }

}

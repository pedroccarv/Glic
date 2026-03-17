package com.pedro.glic.controller;

import com.pedro.glic.dto.InsulinRequestDTO;
import com.pedro.glic.dto.InsulinResponseDTO;
import com.pedro.glic.entity.User;
import com.pedro.glic.security.AuthUtils;
import com.pedro.glic.service.InsulinService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/insulins")
@CrossOrigin(origins = "*")
public class InsulinController {

    private final InsulinService insulinService;
    private final AuthUtils authUtils;

    public InsulinController(InsulinService insulinService, AuthUtils authUtils) {
        this.insulinService = insulinService;
        this.authUtils = authUtils;
    }

    @GetMapping()
    public ResponseEntity<List<InsulinResponseDTO>> getAllInsulins() {
        User user = authUtils.getAuthenticatedUser();
        return ResponseEntity.ok(insulinService.findAllByUserId(user.getId()));
    }

    @GetMapping(value = "/{insulinId}")
    public ResponseEntity<InsulinResponseDTO> getInsulinById(@PathVariable Long insulinId) {
        User user = authUtils.getAuthenticatedUser();
        return ResponseEntity.ok(insulinService.findById(user.getId(),insulinId));
    }

    @PostMapping
    public ResponseEntity<InsulinResponseDTO> createInsulin(@Valid @RequestBody InsulinRequestDTO insulinRequestDTO) {
        User user = authUtils.getAuthenticatedUser();
        return ResponseEntity.status(HttpStatus.CREATED).body(insulinService.createInsulin(user.getId(),insulinRequestDTO));
    }

    @DeleteMapping(value = "/{insulinId}")
    public ResponseEntity<Void> deleteInsulin(@PathVariable Long insulinId) {
        User user = authUtils.getAuthenticatedUser();
        insulinService.deleteInsulin(user.getId(),insulinId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{insulinId}")
    public ResponseEntity<InsulinResponseDTO> updateInsulin(@PathVariable Long insulinId, @Valid @RequestBody InsulinRequestDTO insulinRequestDTO) {
        User user = authUtils.getAuthenticatedUser();
        return ResponseEntity.ok(insulinService.updateInsulin(user.getId(),insulinId,insulinRequestDTO));
    }

}

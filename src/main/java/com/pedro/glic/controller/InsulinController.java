package com.pedro.glic.controller;

import com.pedro.glic.dto.InsulinRequestDTO;
import com.pedro.glic.dto.InsulinResponseDTO;
import com.pedro.glic.service.InsulinService;
import com.pedro.glic.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/insulins")
@CrossOrigin(origins = "*")
public class InsulinController {

    private final InsulinService insulinService;

    public InsulinController(InsulinService insulinService) {
        this.insulinService = insulinService;
    }

    @GetMapping()
    public ResponseEntity<List<InsulinResponseDTO>> getAllInsulins(@PathVariable Long userId) {
        List<InsulinResponseDTO> dto = insulinService.findAllByUserId(userId);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping(value = "/{insulinId}")
    public ResponseEntity<InsulinResponseDTO> getInsulinById(@PathVariable Long userId,@PathVariable Long insulinId) {
        InsulinResponseDTO dto = insulinService.findById(userId,insulinId);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<InsulinResponseDTO> createInsulin(@PathVariable Long userId,@Valid @RequestBody InsulinRequestDTO insulinRequestDTO) {
        InsulinResponseDTO dto = insulinService.createInsulin(userId,insulinRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @DeleteMapping(value = "/{insulinId}")
    public ResponseEntity<Void> deleteInsulin(@PathVariable Long userId,@PathVariable Long insulinId) {
        insulinService.deleteInsulin(userId,insulinId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{insulinId}")
    public ResponseEntity<InsulinResponseDTO> updateInsulin(@PathVariable Long userId,@PathVariable Long insulinId, @Valid @RequestBody InsulinRequestDTO insulinRequestDTO) {
        InsulinResponseDTO dto = insulinService.updateInsulin(userId,insulinId,insulinRequestDTO);
        return ResponseEntity.ok().body(dto);
    }

}

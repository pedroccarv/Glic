package com.pedro.glic.controller;

import com.pedro.glic.dto.ApplicationLogRequestDTO;
import com.pedro.glic.dto.ApplicationLogResponseDTO;
import com.pedro.glic.service.ApplicationLogService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/users/{userId}/logs")
public class ApplicationLogController {

    private final ApplicationLogService applicationLogService;

    public ApplicationLogController(ApplicationLogService applicationLogService) {
        this.applicationLogService = applicationLogService;
    }

    @GetMapping
    public ResponseEntity<List<ApplicationLogResponseDTO>> getLogs(
            @PathVariable Long userId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<ApplicationLogResponseDTO> logs;
        if (date == null) {
            logs = applicationLogService.findAllByUserId(userId);
        } else {
            logs = applicationLogService.findByDate(userId, date);
        }
        return ResponseEntity.ok().body(logs);
    }

    @GetMapping( "/{logId}")
    public ResponseEntity<ApplicationLogResponseDTO> findById(@PathVariable Long userId, @PathVariable Long logId) {
        ApplicationLogResponseDTO log = applicationLogService.findById(userId, logId);
        return ResponseEntity.ok().body(log);
    }

    @PostMapping("/insulina/{insulinId}")
    public ResponseEntity<ApplicationLogResponseDTO> create(
            @PathVariable Long userId,
            @PathVariable Long insulinId,
            @Valid @RequestBody ApplicationLogRequestDTO applicationLogRequestDTO) {
        ApplicationLogResponseDTO response = applicationLogService.createApplication(userId, insulinId, applicationLogRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{logId}")
    public ResponseEntity<ApplicationLogResponseDTO> update(
            @PathVariable Long userId,
            @PathVariable Long logId,
           @Valid @RequestBody ApplicationLogRequestDTO applicationLogRequestDTO) {
        ApplicationLogResponseDTO updateLog = applicationLogService.updateLog(userId, logId, applicationLogRequestDTO);
        return ResponseEntity.ok().body(updateLog);
    }

    @DeleteMapping("/{logId}")
    public ResponseEntity<Void> delete(@PathVariable Long userId, @PathVariable Long logId) {
        applicationLogService.deleteById(userId, logId);
        return ResponseEntity.noContent().build();
    }

}

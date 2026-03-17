package com.pedro.glic.controller;

import com.pedro.glic.dto.ApplicationLogRequestDTO;
import com.pedro.glic.dto.ApplicationLogResponseDTO;
import com.pedro.glic.entity.User;
import com.pedro.glic.security.AuthUtils;
import com.pedro.glic.service.ApplicationLogService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/logs")
@CrossOrigin(origins = "*")
public class ApplicationLogController {

    private final ApplicationLogService applicationLogService;
    private final AuthUtils authUtils;

    public ApplicationLogController(ApplicationLogService applicationLogService, AuthUtils authUtils) {
        this.applicationLogService = applicationLogService;
        this.authUtils = authUtils;
    }

    @GetMapping
    public ResponseEntity<List<ApplicationLogResponseDTO>> getLogs(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        User user = authUtils.getAuthenticatedUser();
        List<ApplicationLogResponseDTO> logs;
        if (date == null) {
            logs = applicationLogService.findAllByUserId(user.getId());
        } else {
            logs = applicationLogService.findByDate(user.getId(), date);
        }
        return ResponseEntity.ok().body(logs);
    }

    @GetMapping( "/{logId}")
    public ResponseEntity<ApplicationLogResponseDTO> findById(@PathVariable Long logId) {
        User user = authUtils.getAuthenticatedUser();
        ApplicationLogResponseDTO log = applicationLogService.findById(user.getId(), logId);
        return ResponseEntity.ok().body(log);
    }

    @PostMapping("/insulina/{insulinId}")
    public ResponseEntity<ApplicationLogResponseDTO> create(
            @PathVariable Long insulinId,
            @Valid @RequestBody ApplicationLogRequestDTO applicationLogRequestDTO) {
        User user = authUtils.getAuthenticatedUser();
        ApplicationLogResponseDTO response = applicationLogService.createApplication(user.getId(), insulinId, applicationLogRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{logId}")
    public ResponseEntity<ApplicationLogResponseDTO> update(
            @PathVariable Long logId,
           @Valid @RequestBody ApplicationLogRequestDTO applicationLogRequestDTO) {
        User user = authUtils.getAuthenticatedUser();
        ApplicationLogResponseDTO updateLog = applicationLogService.updateLog(user.getId(), logId, applicationLogRequestDTO);
        return ResponseEntity.ok().body(updateLog);
    }

    @DeleteMapping("/{logId}")
    public ResponseEntity<Void> delete (@PathVariable Long logId) {
        User user = authUtils.getAuthenticatedUser();
        applicationLogService.deleteById(user.getId(), logId);
        return ResponseEntity.noContent().build();
    }

}

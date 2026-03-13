package com.pedro.glic.service;

import com.pedro.glic.dto.ApplicationLogRequestDTO;
import com.pedro.glic.dto.ApplicationLogResponseDTO;
import com.pedro.glic.entity.ApplicationLog;
import com.pedro.glic.entity.Insulin;
import com.pedro.glic.entity.User;
import com.pedro.glic.repository.ApplicationLogRepository;
import com.pedro.glic.repository.InsulinRepository;
import com.pedro.glic.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicationLogService {

    private final ApplicationLogRepository applicationLogRepository;
    private final UserRepository userRepository;
    private final InsulinRepository insulinRepository;

    public ApplicationLogService(ApplicationLogRepository applicationLogRepository, UserRepository userRepository, InsulinRepository insulinRepository) {
        this.applicationLogRepository = applicationLogRepository;
        this.userRepository = userRepository;
        this.insulinRepository = insulinRepository;
    }

    public ApplicationLogResponseDTO createApplication(Long userId, Long insulinId, ApplicationLogRequestDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario nao encontrado"));
        Insulin insulin = insulinRepository.findById(insulinId)
                .orElseThrow(() -> new RuntimeException("Insulina nao encontrada"));
        if (!insulin.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Esta insulina não pertence a este usuário");
        }
        ApplicationLog applicationLog = new ApplicationLog(
                dto.appliedUnits(),
                dto.applicationTime(),
                dto.consumedCarbs(),
                user,
                insulin
        );
        ApplicationLog applicationLogSaved = applicationLogRepository.save(applicationLog);
        return ApplicationLogResponseDTO.toDTO(applicationLogSaved);
    }

    public List<ApplicationLogResponseDTO> findAllByUserId(Long userId) {
        List<ApplicationLog> logs = applicationLogRepository.findAllByUserId(userId);
        return logs.stream().map(ApplicationLogResponseDTO::toDTO).toList();
    }

    public ApplicationLogResponseDTO findById(Long userId, Long log) {
        ApplicationLog application = getApplicationLogAndVerifyOwnerShip(userId, log);
        return ApplicationLogResponseDTO.toDTO(application);
    }

    public List<ApplicationLogResponseDTO> findByDate(Long userId, LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
        List<ApplicationLog> logs = applicationLogRepository.findByUserIdAndApplicationTimeBetween(userId, startOfDay, endOfDay);

        return logs.stream().map(ApplicationLogResponseDTO::toDTO).toList();
    }

    public ApplicationLogResponseDTO updateLog(Long userId, Long logId,ApplicationLogRequestDTO dto) {
        ApplicationLog log = getApplicationLogAndVerifyOwnerShip(userId, logId);
        log.updateFrom(dto);
        return ApplicationLogResponseDTO.toDTO(applicationLogRepository.save(log));
    }

    public void deleteById(Long userId, Long logId) {
        ApplicationLog application = getApplicationLogAndVerifyOwnerShip(userId, logId);
        applicationLogRepository.delete(application);
    }

    private ApplicationLog getApplicationLogAndVerifyOwnerShip(Long userId, Long log) {
        ApplicationLog applicationLog = applicationLogRepository.findById(log)
                .orElseThrow(() -> new RuntimeException("Log not found"));
        if (!applicationLog.getUser().getId().equals(userId)) {
            throw new RuntimeException("This log does not belong to the user");
        }
        return applicationLog;
    }

}

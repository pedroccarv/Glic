package com.pedro.glic.repository;

import com.pedro.glic.entity.ApplicationLog;
import com.pedro.glic.entity.Insulin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ApplicationLogRepository extends JpaRepository<ApplicationLog, Long> {
    List<ApplicationLog> findAllByUserId(Long userId);
    List<ApplicationLog> findByUserIdAndApplicationTimeBetween(Long userId, LocalDateTime start, LocalDateTime end);
}

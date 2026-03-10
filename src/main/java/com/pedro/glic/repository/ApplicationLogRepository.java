package com.pedro.glic.repository;

import com.pedro.glic.entity.ApplicationLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationLogRepository extends JpaRepository<ApplicationLog, Long> {
}

package com.pedro.glic.repository;

import com.pedro.glic.entity.Insulin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InsulinRepository extends JpaRepository<Insulin, Long> {
    List<Insulin> findAllByUserId(Long userId);
}

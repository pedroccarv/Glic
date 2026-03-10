package com.pedro.glic.repository;

import com.pedro.glic.entity.Insulin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsulinRepository extends JpaRepository<Insulin, Long> {
}

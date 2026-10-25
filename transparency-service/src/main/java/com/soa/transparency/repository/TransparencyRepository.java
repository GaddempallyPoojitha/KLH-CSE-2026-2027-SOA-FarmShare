package com.soa.transparency.repository;

import com.soa.transparency.entity.Transparency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransparencyRepository
        extends JpaRepository<Transparency, Long> {
}
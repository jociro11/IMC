package com.web.imc.repository;

import com.web.imc.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Repository extends JpaRepository<Pessoa, Long> {
}

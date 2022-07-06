package com.backend.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.biblioteca.model.EmprestimoModel;

public interface EmprestimoRepository extends JpaRepository<EmprestimoModel, Integer> {

}

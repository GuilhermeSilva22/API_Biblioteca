package com.backend.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.biblioteca.model.AlunoModel;

@Repository
public interface AlunoRepository extends JpaRepository<AlunoModel, Integer> {

}

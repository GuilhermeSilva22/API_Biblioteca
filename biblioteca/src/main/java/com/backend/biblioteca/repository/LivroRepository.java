package com.backend.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.biblioteca.model.LivroModel;

public interface LivroRepository extends JpaRepository<LivroModel, Integer> {

}

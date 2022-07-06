package com.backend.biblioteca.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.biblioteca.model.FuncionarioModel;

public interface FuncionarioRepository extends JpaRepository<FuncionarioModel, Integer> {

    public Optional<FuncionarioModel> findByCpf(String cpf);
}

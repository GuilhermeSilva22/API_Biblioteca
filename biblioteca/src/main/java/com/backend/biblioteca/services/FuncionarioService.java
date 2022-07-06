package com.backend.biblioteca.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.biblioteca.model.FuncionarioModel;
import com.backend.biblioteca.model.exception.ResourceNotFoundException;
import com.backend.biblioteca.repository.FuncionarioRepository;
import com.backend.biblioteca.shared.FuncionarioDTO;

@Service
public class FuncionarioService {

    @Autowired
    FuncionarioRepository funcionarioRepository;

    public FuncionarioDTO logar(FuncionarioDTO funcionarioDto) {
        Optional<FuncionarioModel> funcionario = funcionarioRepository.findByCpf(funcionarioDto.getCpf());
        if (funcionario == null) {
            throw new ResourceNotFoundException("Funcionario não existe!");
        }
        if (funcionario.get().getSenha() != funcionarioDto.getSenha()) {
            throw new ResourceNotFoundException("As credênciais estão incorretas!");
        }
        return new ModelMapper().map(funcionario.get(), FuncionarioDTO.class);
    }

    public List<FuncionarioDTO> obterTodos() {
        List<FuncionarioModel> funcionarios = funcionarioRepository.findAll();
        return funcionarios.stream()
                .map(funcionario -> new ModelMapper().map(funcionario, FuncionarioDTO.class))
                .collect(Collectors.toList());
    }

    public Optional<FuncionarioDTO> obterPorId(Integer id) {
        // Obtendo um optional de funcionario pelo id.
        Optional<FuncionarioModel> funcionario = funcionarioRepository.findById(id);
        // Se por algum motivo não encontrar, lança uma exception.
        if (funcionario.isEmpty()) {
            throw new ResourceNotFoundException("Funcionario com id: " + id + " Não encontrado");
        }
        // Convertendo o meu optional de funcionarioModel para um funcionarioDTO
        FuncionarioDTO dto = new ModelMapper().map(funcionario.get(), FuncionarioDTO.class);
        // Criando e retornando um um optional de funcionarioiDTO
        return Optional.of(dto);
    }

    public FuncionarioDTO adicionar(FuncionarioDTO funcionarioDto) {
        // Removendo o id para conseguir fazer o cadastro.
        funcionarioDto.setId(null);
        // Criar um objeto para mapear
        ModelMapper mapper = new ModelMapper();
        // Converter o meu FuncionarioDTO em um FuncionarioModel
        FuncionarioModel funcionario = mapper.map(funcionarioDto, FuncionarioModel.class);
        // Salvar o FuncionarioModel no banco
        funcionario = funcionarioRepository.save(funcionario);
        // em vez de converter em DTO de novo, optei por dizer que o id dele vai ser o
        // id que veio do banco.
        funcionarioDto.setId(funcionario.getId());
        // Retornar o FuncionarioDTO atualizado
        return funcionarioDto;
    }

    public void deletar(Integer id) {
        // Antes de tudo tenho que saber se o funcionario existe
        Optional<FuncionarioModel> funcionario = funcionarioRepository.findById(id);
        // Se não existir lança uma exception
        if (funcionario.isEmpty()) {
            throw new ResourceNotFoundException("Não foi possivel deletar o Funcionario com o id: " + id);
        }
        funcionarioRepository.deleteById(id);
    }

    public FuncionarioDTO atualizar(FuncionarioDTO funcionarioDto, Integer id) {
        // passar o id para o alunoDto
        funcionarioDto.setId(id);
        // Criar um objeto para mapeamento
        ModelMapper mapper = new ModelMapper();
        // converter o DTO em um funcionarioModel.
        FuncionarioModel funcionario = mapper.map(funcionarioDto, FuncionarioModel.class);
        // Atualizar o funcionario no banco
        funcionarioRepository.save(funcionario);
        // Retornar o funcionarioDto atualizado
        return funcionarioDto;
    }
}

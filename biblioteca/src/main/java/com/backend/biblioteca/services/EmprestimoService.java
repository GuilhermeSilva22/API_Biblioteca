package com.backend.biblioteca.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.biblioteca.model.EmprestimoModel;
import com.backend.biblioteca.model.exception.ResourceNotFoundException;
import com.backend.biblioteca.repository.EmprestimoRepository;
import com.backend.biblioteca.shared.EmprestimoDTO;

@Service
public class EmprestimoService {
    @Autowired
    private EmprestimoRepository emprestimoRepository;

    public List<EmprestimoDTO> obterTodos() {
        List<EmprestimoModel> emprestimos = emprestimoRepository.findAll();
        return emprestimos.stream()
                .map(emprestimo -> new ModelMapper().map(emprestimo, EmprestimoDTO.class))
                .collect(Collectors.toList());

    }

    public Optional<EmprestimoDTO> obterPorId(Integer id) {
        // Obtendo um optional de emprestimo pelo id.
        Optional<EmprestimoModel> emprestimo = emprestimoRepository.findById(id);
        // Se por algum motivo não encontrar, lança uma exception.
        if (emprestimo.isEmpty()) {
            throw new ResourceNotFoundException("Produto com id: " + id + " Não encontrado");
        }
        // Convertendo o meu optional de emprestimooModel para um emprestimoDTO
        EmprestimoDTO dto = new ModelMapper().map(emprestimo.get(), EmprestimoDTO.class);
        // Criando e retornando um um optional de emprestimoDTO
        return Optional.of(dto);
    }

    public EmprestimoDTO adicionar(EmprestimoDTO emprestimoDto) {
        // Removendo o id para conseguir fazer o cadastro.
        emprestimoDto.setId(null);
        // Criar um objeto para mapear
        ModelMapper mapper = new ModelMapper();
        // Converter o meu EmprestimoDTO em um EmprestimoModel
        EmprestimoModel emprestimo = mapper.map(emprestimoDto, EmprestimoModel.class);
        // Salvar o EmprestimoModel no banco
        emprestimo = emprestimoRepository.save(emprestimo);
        // em vez de converter em DTO de novo, optei por dizer que o id dele vai ser o
        // id que veio do banco.
        emprestimoDto.setId(emprestimo.getId());
        // Retornar o AlunoDTO atualizado
        return emprestimoDto;
    }

    public void deletar(Integer id) {
        // Antes de tudo tenho que saber se o aluno existe
        Optional<EmprestimoModel> emprestimo = emprestimoRepository.findById(id);
        // Se não existir lança uma exception
        if (emprestimo.isEmpty()) {
            throw new ResourceNotFoundException("Não foi possivel deletar o emprestimo com o id: " + id);
        }
        emprestimoRepository.deleteById(id);
    }

    public EmprestimoDTO atualizar(EmprestimoDTO emprestimoDto, Integer id) {
        // passar o id para o emprestimoDto
        emprestimoDto.setId(id);
        // Criar um objeto para mapeamento
        ModelMapper mapper = new ModelMapper();
        // converter o DTO em um emprestimoModel.
        EmprestimoModel emprestimo = mapper.map(emprestimoDto, EmprestimoModel.class);
        // Atualizar o aluno no banco
        emprestimoRepository.save(emprestimo);
        // Retornar o alunoDTo atualizado
        return emprestimoDto;
    }

}

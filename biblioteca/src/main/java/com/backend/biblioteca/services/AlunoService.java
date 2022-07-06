package com.backend.biblioteca.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.biblioteca.model.AlunoModel;
import com.backend.biblioteca.model.exception.ResourceNotFoundException;
import com.backend.biblioteca.repository.AlunoRepository;
import com.backend.biblioteca.shared.AlunoDTO;

@Service
public class AlunoService {
    @Autowired
    private AlunoRepository alunoRepository;

    public List<AlunoDTO> obterTodos() {
        List<AlunoModel> alunos = alunoRepository.findAll();
        return alunos.stream()
                .map(aluno -> new ModelMapper().map(aluno, AlunoDTO.class))
                .collect(Collectors.toList());
    }

    public Optional<AlunoDTO> obterPorId(Integer id) {
        // Obtendo um optional de aluno pelo id.
        Optional<AlunoModel> aluno = alunoRepository.findById(id);
        // Se por algum motivo não encontrar, lança uma exception.
        if (aluno.isEmpty()) {
            throw new ResourceNotFoundException("Produto com id: " + id + " Não encontrado");
        }
        // Convertendo o meu optional de alunoModel para um alunoDTO
        AlunoDTO dto = new ModelMapper().map(aluno.get(), AlunoDTO.class);
        // Criando e retornando um um optional de alunoDTO
        return Optional.of(dto);
    }

    public AlunoDTO adicionar(AlunoDTO alunoDTO) {
        // Removendo o id para conseguir fazer o cadastro.
        alunoDTO.setId(null);
        // Criar um objeto para mapear
        ModelMapper mapper = new ModelMapper();
        // Converter o meu AlunoDTO em um AlunoModel
        AlunoModel aluno = mapper.map(alunoDTO, AlunoModel.class);
        // Salvar o alunoModel no banco
        aluno = alunoRepository.save(aluno);
        // em vez de converter em DTO de novo, optei por dizer que o id dele vai ser o
        // id que veio do banco.
        alunoDTO.setId(aluno.getId());
        // Retornar o AlunoDTO atualizado
        return alunoDTO;
    }

    public void deletar(Integer id) {
        // Antes de tudo tenho que saber se o aluno existe
        Optional<AlunoModel> aluno = alunoRepository.findById(id);
        // Se não existir lança uma exception
        if (aluno.isEmpty()) {
            throw new ResourceNotFoundException("Não foi possivel deletar o produto com o id: " + id);
        }
        alunoRepository.deleteById(id);
    }

    public AlunoDTO atualizar(Integer id, AlunoDTO alunoDto) {
        //passar o id para o alunoDto
        alunoDto.setId(id);
        // Criar um objeto para mapeamento
        ModelMapper mapper = new ModelMapper();
        // converter o DTO em um alunoModel.
        AlunoModel aluno = mapper.map(alunoDto, AlunoModel.class);
        // Atualizar o aluno no banco
        alunoRepository.save(aluno);
        // Retornar o alunoDTo atualizado
        return alunoDto;
    }

}

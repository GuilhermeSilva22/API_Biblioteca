package com.backend.biblioteca.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.biblioteca.model.LivroModel;
import com.backend.biblioteca.model.exception.ResourceNotFoundException;
import com.backend.biblioteca.repository.LivroRepository;
import com.backend.biblioteca.shared.LivroDTO;

@Service
public class LivroService {
    @Autowired
    LivroRepository livroRepository;

    public List<LivroDTO> obterTodos() {
        List<LivroModel> livros = livroRepository.findAll();
        return livros.stream()
                .map(livro -> new ModelMapper().map(livro, LivroDTO.class))
                .collect(Collectors.toList());
    }

    public Optional<LivroDTO> obterPorId(Integer id) {
        // Obtendo um optional de livro pelo id.
        Optional<LivroModel> livro = livroRepository.findById(id);
        // Se por algum motivo não encontrar, lança uma exception.
        if (livro.isEmpty()) {
            throw new ResourceNotFoundException("Livro com id: " + id + " Não encontrado");
        }
        // Convertendo o meu optional de livroModel para um livroDTO
        LivroDTO dto = new ModelMapper().map(livro.get(), LivroDTO.class);
        // Criando e retornando um um optional de livroDTO
        return Optional.of(dto);
    }

    public LivroDTO adicionar(LivroDTO livroDto) {
        // Removendo o id para conseguir fazer o cadastro.
        livroDto.setId(null);
        // Criar um objeto para mapear
        ModelMapper mapper = new ModelMapper();
        // Converter o meu LivroDTO em um LivroModel
        LivroModel livro = mapper.map(livroDto, LivroModel.class);
        // Salvar o livroModel no banco
        livro = livroRepository.save(livro);
        // em vez de converter em DTO de novo, optei por dizer que o id dele vai ser o
        // id que veio do banco.
        livroDto.setId(livro.getId());
        // Retornar o AlunoDTO atualizado
        return livroDto;
    }

    public void deletar(Integer id) {
        // Antes de tudo tenho que saber se o livro existe
        Optional<LivroModel> livro = livroRepository.findById(id);
        // Se não existir lança uma exception
        if (livro.isEmpty()) {
            throw new ResourceNotFoundException("Não foi possivel deletar o livro com o id: " + id);
        }
        livroRepository.deleteById(id);
    }

    public LivroDTO atualizar(LivroDTO livroDto, Integer id) {
        // passar o id para o livroDto
        livroDto.setId(id);
        // Criar um objeto para mapeamento
        ModelMapper mapper = new ModelMapper();
        // converter o DTO em um alivroModel.
        LivroModel livro = mapper.map(livroDto, LivroModel.class);
        // Atualizar o livro no banco
        livroRepository.save(livro);
        // Retornar o livroDTo atualizado
        return livroDto;
    }

}

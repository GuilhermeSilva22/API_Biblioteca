package com.backend.biblioteca.services.view.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.biblioteca.services.LivroService;
import com.backend.biblioteca.services.view.model.LivroRequest;
import com.backend.biblioteca.services.view.model.LivroResponse;
import com.backend.biblioteca.shared.LivroDTO;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/livros")
public class LivroController {

    @Autowired
    private LivroService livroService;

    @GetMapping
    public ResponseEntity<List<LivroResponse>> obterTodos() {
        List<LivroDTO> livros = livroService.obterTodos();
        ModelMapper mapper = new ModelMapper();
        List<LivroResponse> resposta = livros.stream()
                .map(livro -> mapper.map(livro, LivroResponse.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(resposta, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<LivroResponse> adicionar(@RequestBody LivroRequest livroReq) {
        ModelMapper mapper = new ModelMapper();
        LivroDTO livroDto = mapper.map(livroReq, LivroDTO.class);
        livroDto = livroService.adicionar(livroDto);
        return new ResponseEntity<>(mapper.map(livroDto, LivroResponse.class), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<LivroResponse>> obterPorId(@PathVariable Integer id) {
        try {
            Optional<LivroDTO> dto = livroService.obterPorId(id);
            LivroResponse livro = new ModelMapper().map(dto.get(), LivroResponse.class);
            return new ResponseEntity<>(Optional.of(livro), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Integer id) {
        livroService.deletar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LivroResponse> atualizar(@RequestBody LivroRequest livroReq, @PathVariable Integer id) {
        ModelMapper mapper = new ModelMapper();
        LivroDTO livroDto = mapper.map(livroReq, LivroDTO.class);

        livroDto = livroService.atualizar(livroDto, id);
        return new ResponseEntity<>(
                mapper.map(livroDto, LivroResponse.class),
                HttpStatus.OK);
    }
}

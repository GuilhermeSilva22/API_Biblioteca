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

import com.backend.biblioteca.services.FuncionarioService;
import com.backend.biblioteca.services.view.model.FuncionarioRequest;
import com.backend.biblioteca.services.view.model.FuncionarioResponse;
import com.backend.biblioteca.shared.FuncionarioDTO;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/funcionarios")
public class FuncionarioController {
    @Autowired
    private FuncionarioService funcionarioService;

    @PostMapping("/logar")
    public ResponseEntity<FuncionarioResponse> logar(@RequestBody FuncionarioRequest funcionarioReq) {
        ModelMapper mapper = new ModelMapper();
        FuncionarioDTO funcionarioDto = mapper.map(funcionarioReq, FuncionarioDTO.class);
        funcionarioDto = funcionarioService.logar(funcionarioDto);
        return new ResponseEntity<>(mapper.map(funcionarioDto, FuncionarioResponse.class), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<FuncionarioResponse>> obterTodos() {
        List<FuncionarioDTO> funcionarios = funcionarioService.obterTodos();
        ModelMapper mapper = new ModelMapper();
        List<FuncionarioResponse> resposta = funcionarios.stream()
                .map(funcionario -> mapper.map(funcionario, FuncionarioResponse.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(resposta, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<FuncionarioResponse> adicionar(@RequestBody FuncionarioRequest funcionarioReq) {
        ModelMapper mapper = new ModelMapper();
        FuncionarioDTO funcionarioDto = mapper.map(funcionarioReq, FuncionarioDTO.class);
        funcionarioDto = funcionarioService.adicionar(funcionarioDto);
        return new ResponseEntity<>(mapper.map(funcionarioDto, FuncionarioResponse.class), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<FuncionarioResponse>> obterPorId(@PathVariable Integer id) {
        try {
            Optional<FuncionarioDTO> dto = funcionarioService.obterPorId(id);
            FuncionarioResponse funcionario = new ModelMapper().map(dto.get(), FuncionarioResponse.class);
            return new ResponseEntity<>(Optional.of(funcionario), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Integer id) {
        funcionarioService.deletar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FuncionarioResponse> atualizar(@RequestBody FuncionarioRequest funcionarioReq,
            @PathVariable Integer id) {
        ModelMapper mapper = new ModelMapper();
        FuncionarioDTO funcionarioDto = mapper.map(funcionarioReq, FuncionarioDTO.class);

        funcionarioDto = funcionarioService.atualizar(funcionarioDto, id);
        return new ResponseEntity<>(
                mapper.map(funcionarioDto, FuncionarioResponse.class),
                HttpStatus.OK);
    }
}

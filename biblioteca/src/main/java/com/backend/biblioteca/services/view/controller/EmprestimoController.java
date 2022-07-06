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

import com.backend.biblioteca.services.EmprestimoService;
import com.backend.biblioteca.services.view.model.EmprestimoRequest;
import com.backend.biblioteca.services.view.model.EmprestimoResponse;
import com.backend.biblioteca.shared.EmprestimoDTO;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/emprestimos")
public class EmprestimoController {
    @Autowired
    private EmprestimoService emprestimoService;

    @GetMapping
    public ResponseEntity<List<EmprestimoResponse>> obterTodos() {
        List<EmprestimoDTO> emprestimos = emprestimoService.obterTodos();
        ModelMapper mapper = new ModelMapper();
        List<EmprestimoResponse> resposta = emprestimos.stream()
                .map(emprestimo -> mapper.map(emprestimo, EmprestimoResponse.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(resposta, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<EmprestimoResponse> adicionar(@RequestBody EmprestimoRequest emprestimoReq) {
        ModelMapper mapper = new ModelMapper();
        EmprestimoDTO emprestimoDto = mapper.map(emprestimoReq, EmprestimoDTO.class);
        emprestimoDto = emprestimoService.adicionar(emprestimoDto);
        return new ResponseEntity<>(mapper.map(emprestimoDto, EmprestimoResponse.class), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<EmprestimoResponse>> obterPorId(@PathVariable Integer id) {
        try {
            Optional<EmprestimoDTO> dto = emprestimoService.obterPorId(id);
            EmprestimoResponse emprestimo = new ModelMapper().map(dto.get(), EmprestimoResponse.class);
            return new ResponseEntity<>(Optional.of(emprestimo), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Integer id) {
        emprestimoService.deletar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmprestimoResponse> atualizar(@RequestBody EmprestimoRequest emprestimoReq,
            @PathVariable Integer id) {
        ModelMapper mapper = new ModelMapper();
        EmprestimoDTO emprestimoDto = mapper.map(emprestimoReq, EmprestimoDTO.class);

        emprestimoDto = emprestimoService.atualizar(emprestimoDto, id);
        return new ResponseEntity<>(
                mapper.map(emprestimoDto, EmprestimoResponse.class),
                HttpStatus.OK);
    }
}

package com.backend.biblioteca.services.view.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AlunoResponse {
    private Integer id;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private String turno;
}

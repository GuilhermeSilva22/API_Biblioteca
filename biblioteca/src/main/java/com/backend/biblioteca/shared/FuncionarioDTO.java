package com.backend.biblioteca.shared;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FuncionarioDTO {
    private Integer id;
    private String nome;
    private String cpf;
    private String senha;
}

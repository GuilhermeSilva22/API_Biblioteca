package com.backend.biblioteca.shared;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LivroDTO {
    private Integer id;
    private String titulo;
    private Integer quantidade;
    private String estante;
    private String prateleira;
}

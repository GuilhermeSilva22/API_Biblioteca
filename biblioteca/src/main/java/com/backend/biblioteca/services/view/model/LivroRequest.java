package com.backend.biblioteca.services.view.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LivroRequest {
    private String titulo;
    private Integer quantidade;
    private String estante;
    private String prateleira;
}

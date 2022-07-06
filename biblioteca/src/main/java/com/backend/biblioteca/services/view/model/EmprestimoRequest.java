package com.backend.biblioteca.services.view.model;

import java.util.Calendar;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmprestimoRequest {
    private Calendar data_emprestimo;
    private Calendar data_vencimento;
}

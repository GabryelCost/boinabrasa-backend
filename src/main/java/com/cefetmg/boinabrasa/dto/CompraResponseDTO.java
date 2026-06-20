package com.cefetmg.boinabrasa.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompraResponseDTO {
    private Long id;
    private LocalDate dataCompra;
    private BigDecimal valorCompra;
    private String nomeFornecedor;
}

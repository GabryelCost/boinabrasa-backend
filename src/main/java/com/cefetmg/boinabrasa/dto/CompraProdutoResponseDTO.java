package com.cefetmg.boinabrasa.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompraProdutoResponseDTO {
    private Long idProduto;
    private String descricaoProduto;
    private BigDecimal quantidade;
    private BigDecimal valor;
}

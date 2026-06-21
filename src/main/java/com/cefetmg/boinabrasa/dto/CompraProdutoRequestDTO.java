package com.cefetmg.boinabrasa.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CompraProdutoRequestDTO {
    @NotNull(message = "O produto do item é obrigatório.")
    private Long idProduto;

    @NotNull(message = "A quantidade é obrigatória.")
    @Positive(message = "A quantidade tem que ser maior que zero.")
    private BigDecimal quantidade;

    @NotNull(message = "O valor do item é obrigatório.")
    @Positive(message = "O valor do item tem que ser maior que zero.")
    private BigDecimal valor;
}

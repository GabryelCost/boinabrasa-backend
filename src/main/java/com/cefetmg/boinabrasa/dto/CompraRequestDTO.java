package com.cefetmg.boinabrasa.dto;

import java.time.LocalDate;
import java.util.List;
import java.math.BigDecimal;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CompraRequestDTO {

    @NotNull(message="A data da compra é obrigatória.")
    private LocalDate dataCompra;

    @NotNull(message = "O valor da compra é obrigatório.")
    private BigDecimal valorCompra;

    @NotNull(message = "O identificador do fornecedor é obrigatório.")
    private Long idFornecedor;

    @NotEmpty(message = "A compra precisa ter pelo menos um item.")
    private List<@Valid CompraProdutoRequestDTO> itens;
}

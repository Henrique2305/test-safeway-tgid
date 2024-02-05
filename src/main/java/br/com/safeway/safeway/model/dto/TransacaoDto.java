package br.com.safeway.safeway.model.dto;

import java.math.BigDecimal;

public record TransacaoDto(
        Long idCliente,
        String cnpj,
        BigDecimal valor
) {
}

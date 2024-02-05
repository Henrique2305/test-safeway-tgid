package br.com.safeway.safeway.model.dto;

import br.com.safeway.safeway.model.Empresa;

import java.math.BigDecimal;

public record EmpresaDto(
        Long id,
        String razaoSocial,
        String CNPJ,
        BigDecimal saldo
) {
    public EmpresaDto(Empresa empresa) {
        this(
                empresa.getId(),
                empresa.getRazaoSocial(),
                empresa.getCNPJ(),
                empresa.getSaldo()
        );
    }
}

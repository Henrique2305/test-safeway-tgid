package br.com.safeway.safeway.model.dto;

import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.br.CNPJ;

import java.math.BigDecimal;

public record CadastroEAtualizacaoEmpresaDto(
        @NotEmpty(message = "O nome não pode estar vazio")
        String razaoSocial,
        @CNPJ(message = "CNPJ inválido")
        String CNPJ,
        BigDecimal saldo
) {
}

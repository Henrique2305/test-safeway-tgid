package br.com.safeway.safeway.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.br.CPF;

public record CadastroEAtualizacaoClienteDto(
        @NotEmpty(message = "O nome não pode estar vazio")
        String nome,
        @CPF(message = "CPF inválido")
        String CPF,
        @Pattern(regexp = "\\(?\\d{2}\\)?\\d?\\d{4}-?\\d{4}", message = "Telefone inválido")
        String telefone,
        @Email(message = "Email inválido")
        String email,
        @NotNull
        Long idEmpresa
) {
}

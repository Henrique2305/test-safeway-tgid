package br.com.safeway.safeway.model.dto;

import br.com.safeway.safeway.model.Cliente;
import br.com.safeway.safeway.model.Empresa;

public record ClienteDto(
        Long id,
        String nome,
        String CPF,
        String telefone,
        String email,
        String nomeEmpresa
) {

    public ClienteDto(Cliente cliente) {
        this(cliente.getId(),
        cliente.getNome(),
        cliente.getCPF(),
        cliente.getTelefone(),
        cliente.getEmail(),
        cliente.getEmpresa().getRazaoSocial());
    }
}

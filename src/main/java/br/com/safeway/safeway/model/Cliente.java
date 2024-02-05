package br.com.safeway.safeway.model;

import br.com.safeway.safeway.model.dto.CadastroEAtualizacaoClienteDto;
import jakarta.persistence.*;

@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String CPF;
    private String telefone;
    private String email;
    @ManyToOne(fetch = FetchType.LAZY)
    private Empresa empresa;

    public Cliente() {}

    public Cliente(String nome, String CPF, String telefone, String email) {
        this.nome = nome;
        this.CPF = CPF;
        this.telefone = telefone;
        this.email = email;
    }

    public Cliente(CadastroEAtualizacaoClienteDto dto) {
        this.nome = dto.nome();
        this.CPF = dto.CPF();
        this.telefone = dto.telefone();
        this.email = dto.email();
    }

    public Cliente(CadastroEAtualizacaoClienteDto dto, Empresa empresa) {
        this.nome = dto.nome();
        this.CPF = dto.CPF();
        this.telefone = dto.telefone();
        this.email = dto.email();
        this.empresa = empresa;
    }

    public static Cliente mapper(Long id, CadastroEAtualizacaoClienteDto dto, Empresa empresa) {
        Cliente cliente = new Cliente(dto, empresa);
        cliente.setId(id);
        return cliente;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCPF() {
        return CPF;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public Empresa getEmpresa() {
        return empresa;
    }
}

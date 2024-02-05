package br.com.safeway.safeway.model;

import br.com.safeway.safeway.model.dto.CadastroEAtualizacaoEmpresaDto;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "empresas")
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String razaoSocial;
    private String CNPJ;
    private BigDecimal saldo = BigDecimal.ZERO;
    @OneToMany(mappedBy = "empresa")
    private List<Cliente> clientes = new ArrayList<>();

    public Empresa() {
    }

    public Empresa(String razaoSocial, String CNPJ, BigDecimal saldo) {
        this.razaoSocial = razaoSocial;
        this.CNPJ = CNPJ;
        this.saldo = saldo;
    }

    public Empresa(CadastroEAtualizacaoEmpresaDto dto) {
        this.razaoSocial = dto.razaoSocial();
        this.CNPJ = dto.CNPJ();

        if (dto.saldo() != null ) {
            this.saldo = dto.saldo();
        }

        this.saldo = BigDecimal.ZERO;
    }

    public static Empresa mapper(Long id, CadastroEAtualizacaoEmpresaDto dto) {
        Empresa empresa = new Empresa(dto);
        empresa.setId(id);
        return empresa;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public String getCNPJ() {
        return CNPJ;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void adicionarValorAoSaldo(BigDecimal valor) {
        this.saldo = this.saldo.add(valor);
    }

    public void subtrairValorDoSaldo(BigDecimal valor) {
        this.saldo = this.saldo.subtract(valor);
    }
}

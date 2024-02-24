package br.com.safeway.safeway.model;

import jakarta.persistence.*;

@Entity
@Table(name = "taxas")
public class Taxa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idEmpresa;
    private String taxa;

    public Taxa() {
    }

    public Taxa(Long idEmpresa, String taxa) {
        this.idEmpresa = idEmpresa;
        this.taxa = taxa;
    }

    public String getTaxa() {
        return taxa;
    }

    public void setTaxa(String taxa) {
        this.taxa = taxa;
    }

    public Long getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Long idEmpresa) {
        this.idEmpresa = idEmpresa;
    }
}

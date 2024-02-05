package br.com.safeway.safeway.model;

public enum Tipo {
    DEPOSITO("Deposito"),
    SACADO("Sacado");

    private final String nome;

    Tipo(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}

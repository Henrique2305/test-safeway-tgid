package br.com.safeway.safeway.model;

public enum Taxa {
    DEZ("10");

    private final String valor;

    Taxa(String number) {
        this.valor = number;
    }

    public String getValor() {
        return valor;
    }
}

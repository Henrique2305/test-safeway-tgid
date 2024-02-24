package br.com.safeway.safeway.service;

import br.com.safeway.safeway.model.Tipo;

import java.math.BigDecimal;

public interface Alert {

    void alert(Tipo tipo, String cliente, String documento, BigDecimal valor, String url);
}

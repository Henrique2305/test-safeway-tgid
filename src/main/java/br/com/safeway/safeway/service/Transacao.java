package br.com.safeway.safeway.service;

import br.com.safeway.safeway.model.dto.TransacaoDto;

public interface Transacao {
    void depositar(TransacaoDto dto);
    void sacar(TransacaoDto dto);
}

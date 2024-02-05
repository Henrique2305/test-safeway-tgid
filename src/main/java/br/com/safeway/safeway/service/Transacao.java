package br.com.safeway.safeway.service;

import br.com.safeway.safeway.model.dto.TransacaoDto;
import org.springframework.mail.javamail.JavaMailSender;

import java.math.BigDecimal;

public interface Transacao {
    void depositar(TransacaoDto dto);
    void sacar(TransacaoDto dto);
}

package br.com.safeway.safeway.controller;

import br.com.safeway.safeway.model.dto.TransacaoDto;
import br.com.safeway.safeway.service.TransacaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    @Autowired
    private TransacaoImpl transacaoService;

    @PostMapping("/depositar")
    public void depositar(@RequestBody TransacaoDto dto) {
        transacaoService.depositar(dto);
    }

    @PostMapping("/sacar")
    public void sacar(@RequestBody TransacaoDto dto) {
        transacaoService.sacar(dto);
    }
}

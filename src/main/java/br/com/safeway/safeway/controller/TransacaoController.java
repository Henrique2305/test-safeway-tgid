package br.com.safeway.safeway.controller;

import br.com.safeway.safeway.exception.ValidacaoException;
import br.com.safeway.safeway.model.dto.TransacaoDto;
import br.com.safeway.safeway.service.TransacaoImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Transacao", description = "API da Transacao")
@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    @Autowired
    private TransacaoImpl transacaoService;

    @Operation(
            summary = "Depositar na empresa",
            description = "faz o deposito do valor passado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @PostMapping("/depositar")
    public ResponseEntity depositar(@RequestBody TransacaoDto dto) {
        try {
            transacaoService.depositar(dto);
            return ResponseEntity.ok().build();
        } catch (ValidacaoException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @Operation(
            summary = "Sacar da empresa",
            description = "faz o saque do valor passado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @PostMapping("/sacar")
    public ResponseEntity sacar(@RequestBody TransacaoDto dto) {
        try {
            transacaoService.sacar(dto);
            return ResponseEntity.ok().build();
        } catch (ValidacaoException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }
}

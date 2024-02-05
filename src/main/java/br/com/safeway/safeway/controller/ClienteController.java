package br.com.safeway.safeway.controller;

import br.com.safeway.safeway.exception.ValidacaoException;
import br.com.safeway.safeway.model.dto.CadastroEAtualizacaoClienteDto;
import br.com.safeway.safeway.model.dto.ClienteDto;
import br.com.safeway.safeway.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Cliente", description = "API do Cliente")
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Operation(
            summary = "retorna todos os clientes",
            description = "traz todos os clientes cadastrados no banco")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @GetMapping
    public ResponseEntity<List<ClienteDto>> listAll() {
        return ResponseEntity.ok(clienteService.listAll());
    }

    @Operation(
            summary = "Retorna o cliente por ID",
            description = "traz o cliente passado pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ClienteDto> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(clienteService.findById(id));
    }

    @Operation(
            summary = "Cadastra o cliente",
            description = "faz o cadastramento do cliente no banco")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "successful operation")
    })
    @PostMapping
    public ResponseEntity save(@RequestBody @Valid CadastroEAtualizacaoClienteDto dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.save(dto));
        } catch (ValidacaoException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @Operation(
            summary = "Atualiza o cliente",
            description = "faz a atualizacao dos dados do cliente no banco")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ClienteDto> fullUpdate(@PathVariable("id") Long id, @RequestBody @Valid CadastroEAtualizacaoClienteDto dto) {
        return ResponseEntity.ok(clienteService.fullUpdate(id, dto));
    }

    @Operation(
            summary = "Delete o cliente",
            description = "deleta o cliente passado pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable("id") Long id) {
        clienteService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}

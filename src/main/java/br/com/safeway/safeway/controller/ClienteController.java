package br.com.safeway.safeway.controller;

import br.com.safeway.safeway.exception.ValidacaoException;
import br.com.safeway.safeway.model.dto.CadastroEAtualizacaoClienteDto;
import br.com.safeway.safeway.model.dto.ClienteDto;
import br.com.safeway.safeway.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<ClienteDto>> listAll() {
        return ResponseEntity.ok(clienteService.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDto> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(clienteService.findById(id));
    }

    @PostMapping
    public ResponseEntity save(@RequestBody @Valid CadastroEAtualizacaoClienteDto dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.save(dto));
        } catch (ValidacaoException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDto> fullUpdate(@PathVariable("id") Long id, @RequestBody @Valid CadastroEAtualizacaoClienteDto dto) {
        return ResponseEntity.ok(clienteService.fullUpdate(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable("id") Long id) {
        clienteService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}

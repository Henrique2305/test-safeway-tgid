package br.com.safeway.safeway.controller;

import br.com.safeway.safeway.exception.ValidacaoException;
import br.com.safeway.safeway.model.dto.CadastroEAtualizacaoEmpresaDto;
import br.com.safeway.safeway.model.dto.EmpresaDto;
import br.com.safeway.safeway.service.EmpresaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empresas")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @GetMapping
    public ResponseEntity<List<EmpresaDto>> listAll() {
        return ResponseEntity.ok(empresaService.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpresaDto> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(empresaService.findById(id));
    }

    @PostMapping
    public ResponseEntity save(@RequestBody @Valid CadastroEAtualizacaoEmpresaDto dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(empresaService.save(dto));
        } catch (ValidacaoException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpresaDto> fullUpdate(@PathVariable("id") Long id, @RequestBody @Valid CadastroEAtualizacaoEmpresaDto dto ) {
        return ResponseEntity.ok(empresaService.fullUpdate(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable("id") Long id) {
        empresaService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}

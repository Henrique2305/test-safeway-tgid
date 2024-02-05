package br.com.safeway.safeway.controller;

import br.com.safeway.safeway.exception.ValidacaoException;
import br.com.safeway.safeway.model.dto.CadastroEAtualizacaoEmpresaDto;
import br.com.safeway.safeway.model.dto.EmpresaDto;
import br.com.safeway.safeway.service.EmpresaService;
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

@Tag(name = "Empresa", description = "API da Empresa")
@RestController
@RequestMapping("/empresas")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @Operation(
            summary = "Retorna todas as empresas",
            description = "traz todas as empresas cadastradas no banco")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @GetMapping
    public ResponseEntity<List<EmpresaDto>> listAll() {
        return ResponseEntity.ok(empresaService.listAll());
    }

    @Operation(
            summary = "Retorna empresa por ID",
            description = "traz a empresa de acordo com o ID passado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EmpresaDto> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(empresaService.findById(id));
    }

    @Operation(
            summary = "Cadastra a empresa",
            description = "faz o cadastramento da empresa no banco")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "successful operation")
    })
    @PostMapping
    public ResponseEntity save(@RequestBody @Valid CadastroEAtualizacaoEmpresaDto dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(empresaService.save(dto));
        } catch (ValidacaoException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @Operation(
            summary = "Atualiza a empresa",
            description = "Faz a atualização dos dados da empresa no banco")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EmpresaDto> fullUpdate(@PathVariable("id") Long id, @RequestBody @Valid CadastroEAtualizacaoEmpresaDto dto ) {
        return ResponseEntity.ok(empresaService.fullUpdate(id, dto));
    }

    @Operation(
            summary = "Deleta a empresa",
            description = "deleta a empresa passada pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable("id") Long id) {
        empresaService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}

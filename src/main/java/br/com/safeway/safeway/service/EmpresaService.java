package br.com.safeway.safeway.service;

import br.com.safeway.safeway.exception.ValidacaoException;
import br.com.safeway.safeway.model.Empresa;
import br.com.safeway.safeway.model.dto.CadastroEAtualizacaoEmpresaDto;
import br.com.safeway.safeway.model.dto.EmpresaDto;
import br.com.safeway.safeway.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;


    public List<EmpresaDto> listAll() {
        return empresaRepository.findAll().stream()
                .map(EmpresaDto::new)
                .toList();
    }

    public EmpresaDto save(CadastroEAtualizacaoEmpresaDto dto) {
        if (empresaRepository.existsByCNPJ(dto.CNPJ())) {
            throw new ValidacaoException("Empresa já cadastrada com este CNPJ");
        }

        Empresa empresa = empresaRepository.save(new Empresa(dto));
        return new EmpresaDto(empresa);
    }

    public EmpresaDto findById(Long id) {
        Optional<Empresa> empresa = empresaRepository.findById(id);

        if (empresa.isPresent()) {
            return new EmpresaDto(empresa.get());
        } else {
            throw new ValidacaoException("ID inválido");
        }
    }

    public EmpresaDto fullUpdate(Long id, CadastroEAtualizacaoEmpresaDto dto) {
        Empresa empresa;

        if (empresaRepository.findById(id).isPresent()) {
            empresa = empresaRepository.save(Empresa.mapper(id, dto));
            return new EmpresaDto(empresa);
        } else {
            throw new ValidacaoException("ID inválido");
        }
    }

    public void deleteById(Long id) {
        Optional<Empresa> empresa = empresaRepository.findById(id);

        if (empresa.isPresent()) {
            empresaRepository.deleteById(id);
        } else {
            throw new ValidacaoException("ID inválido");
        }
    }
}

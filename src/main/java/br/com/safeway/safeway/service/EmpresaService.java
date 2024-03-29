package br.com.safeway.safeway.service;

import br.com.safeway.safeway.exception.ValidacaoException;
import br.com.safeway.safeway.model.Empresa;
import br.com.safeway.safeway.model.Taxa;
import br.com.safeway.safeway.model.dto.CadastroEAtualizacaoEmpresaDto;
import br.com.safeway.safeway.model.dto.EmpresaDto;
import br.com.safeway.safeway.repository.EmpresaRepository;
import br.com.safeway.safeway.repository.TaxaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private TaxaService taxaService;

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

        Taxa taxa = new Taxa(empresa.getId(), dto.taxa());
        taxaService.save(taxa);

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

    public Optional<Empresa> findByIdRepository(Long id) {
        return empresaRepository.findById(id);
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

    public Optional<Empresa> findByCNPJ(String cnpj) {
        return empresaRepository.findByCNPJ(cnpj);
    }

    public void saveRepository(Empresa empresa) {
        empresaRepository.save(empresa);
    }
}

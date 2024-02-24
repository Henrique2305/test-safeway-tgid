package br.com.safeway.safeway.service;

import br.com.safeway.safeway.model.Taxa;
import br.com.safeway.safeway.repository.TaxaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.util.Optional;

@Service
public class TaxaService {

    @Autowired
    private TaxaRepository taxaRepository;

    public Taxa save(Taxa taxa) {
        return taxaRepository.save(taxa);
    }


    public Optional<Taxa> findByIdEmpresa(Long id) {
        return taxaRepository.findByIdEmpresa(id);
    }
}

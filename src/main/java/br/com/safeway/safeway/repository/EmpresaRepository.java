package br.com.safeway.safeway.repository;

import br.com.safeway.safeway.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    boolean existsByCNPJ(String CNPJ);

    Optional<Empresa> findByCNPJ(String CNPJ);
}

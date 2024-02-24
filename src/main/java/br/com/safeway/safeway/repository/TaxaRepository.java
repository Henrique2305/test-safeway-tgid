package br.com.safeway.safeway.repository;

import br.com.safeway.safeway.model.Taxa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaxaRepository extends JpaRepository<Taxa, Long> {

    Optional<Taxa> findByIdEmpresa(Long id);
}

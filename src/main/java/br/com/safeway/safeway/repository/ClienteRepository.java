package br.com.safeway.safeway.repository;

import br.com.safeway.safeway.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    boolean existsByCPF(String CPF);

    Optional<Cliente> findByCPF(String CPF);
}

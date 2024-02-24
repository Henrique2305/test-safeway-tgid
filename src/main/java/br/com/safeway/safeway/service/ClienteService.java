package br.com.safeway.safeway.service;

import br.com.safeway.safeway.exception.ValidacaoException;
import br.com.safeway.safeway.model.Cliente;
import br.com.safeway.safeway.model.Empresa;
import br.com.safeway.safeway.model.dto.CadastroEAtualizacaoClienteDto;
import br.com.safeway.safeway.model.dto.ClienteDto;
import br.com.safeway.safeway.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EmpresaService empresaService;

    public List<ClienteDto> listAll() {
        return clienteRepository.findAll().stream()
                .map(ClienteDto::new)
                .toList();
    }

    public ClienteDto save(CadastroEAtualizacaoClienteDto dto) {
        Optional<Empresa> empresa = empresaService.findByIdRepository(dto.idEmpresa());

        Cliente cliente = clienteRepository.save(new Cliente(dto, empresa.get()));
        return new ClienteDto(cliente);
    }

    public ClienteDto findById(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);

        if (cliente.isPresent()) {
            return new ClienteDto(cliente.get());
        } else {
            throw new ValidacaoException("ID inválido");
        }
    }

    public Optional<Cliente> findByIdRepository(Long id) {
        return clienteRepository.findById(id);
    }

    public ClienteDto fullUpdate(Long id, CadastroEAtualizacaoClienteDto dto) {
        Cliente cliente;

        if (clienteRepository.findById(id).isPresent()) {
            Optional<Empresa> empresa = empresaService.findByIdRepository(dto.idEmpresa());
            cliente = clienteRepository.save(Cliente.mapper(id, dto, empresa.get()));
            return new ClienteDto(cliente);
        } else {
            throw new ValidacaoException("ID inválido");
        }
    }

    public void deleteById(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);

        if (cliente.isPresent()) {
            clienteRepository.deleteById(id);
        } else {
            throw new ValidacaoException("ID inválido");
        }
    }
}

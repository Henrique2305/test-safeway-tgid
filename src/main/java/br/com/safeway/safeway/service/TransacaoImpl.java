package br.com.safeway.safeway.service;

import br.com.safeway.safeway.exception.ValidacaoException;
import br.com.safeway.safeway.model.Cliente;
import br.com.safeway.safeway.model.Empresa;
import br.com.safeway.safeway.model.Taxa;
import br.com.safeway.safeway.model.Tipo;
import br.com.safeway.safeway.model.dto.TransacaoDto;
import br.com.safeway.safeway.repository.ClienteRepository;
import br.com.safeway.safeway.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Service
public class TransacaoImpl implements Transacao {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Override
    public void depositar(TransacaoDto dto) {
        Optional<Cliente> cliente = clienteRepository.findById(dto.idCliente());
        Optional<Empresa> empresa = empresaRepository.findByCNPJ(dto.cnpj());

        if (!cliente.isPresent() && !empresa.isPresent()) {
            throw new ValidacaoException("CPF ou CNPJ inválidos");
        }

        validarValor(dto.valor());
        empresa.get().adicionarValorAoSaldo(dto.valor());
        empresaRepository.save(empresa.get());

        System.out.println("Valor depositado: R$ " + dto.valor().setScale(2, RoundingMode.HALF_UP));

        TransactionAlert.alert(Tipo.DEPOSITO, cliente.get().getNome(), empresa.get().getCNPJ(), dto.valor());
        SMSAlert.alert(Tipo.DEPOSITO, cliente.get().getNome(), cliente.get().getCPF(), dto.valor());
    }

    @Override
    public void sacar(TransacaoDto dto) {
        Optional<Cliente> cliente = clienteRepository.findById(dto.idCliente());
        Optional<Empresa> empresa = empresaRepository.findByCNPJ(dto.cnpj());

        if (!cliente.isPresent() && !empresa.isPresent()) {
            throw new ValidacaoException("CPF ou CNPJ inválidos");
        }

        validarValor(dto.valor());
        BigDecimal valorCorrigido = taxarValor(dto.valor());
        BigDecimal saldoAtual = empresa.get().getSaldo();

        if (saldoAtual.compareTo(valorCorrigido) > 0) {
            empresa.get().subtrairValorDoSaldo(valorCorrigido);
        } else {
            System.out.println("Valor corrigido: R$ " + valorCorrigido.setScale(2, RoundingMode.HALF_UP));
            System.out.println("Saldo atual: R$ " + saldoAtual.setScale(2, RoundingMode.HALF_UP));
            throw new ValidacaoException("Saldo insuficiente");
        }

        empresaRepository.save(empresa.get());

        System.out.println("Valor sacado: R$ " + valorCorrigido.setScale(2, RoundingMode.HALF_UP));

        TransactionAlert.alert(Tipo.SACADO, cliente.get().getNome(), empresa.get().getCNPJ(), dto.valor());
        SMSAlert.alert(Tipo.SACADO, cliente.get().getNome(), cliente.get().getCPF(), dto.valor());
    }

    private void validarValor(BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidacaoException("O valor não pode ser negativo");
        }
    }

    private BigDecimal taxarValor(BigDecimal valor) {
        // O valor da taxa é 10 reais
        BigDecimal taxa = new BigDecimal(Taxa.DEZ.getValor());
        valor = valor.add(taxa);
        return valor;
    }
}

package br.com.safeway.safeway.service;

import br.com.safeway.safeway.exception.ValidacaoException;
import br.com.safeway.safeway.model.Cliente;
import br.com.safeway.safeway.model.Empresa;
import br.com.safeway.safeway.model.Taxa;
import br.com.safeway.safeway.model.Tipo;
import br.com.safeway.safeway.model.dto.TransacaoDto;
import br.com.safeway.safeway.repository.ClienteRepository;
import br.com.safeway.safeway.repository.EmpresaRepository;
import br.com.safeway.safeway.repository.TaxaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Service
public class TransacaoImpl implements Transacao {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private TaxaService taxaService;

    @Value("${webhook.url}")
    String webhookURL;

    @Override
    public void depositar(TransacaoDto dto) {
        Alert trasactionAlert = new TransactionAlert();
        Alert smsAlert = new SMSAlert();

        Optional<Cliente> cliente = clienteService.findByIdRepository(dto.idCliente());
        Optional<Empresa> empresa = empresaService.findByCNPJ(dto.cnpj());

        if (!cliente.isPresent() && !empresa.isPresent()) {
            throw new ValidacaoException("CPF ou CNPJ inválidos");
        }

        validarValor(dto.valor());
        empresa.get().adicionarValorAoSaldo(dto.valor());
        empresaService.saveRepository(empresa.get());

        System.out.println("Valor depositado: R$ " + dto.valor().setScale(2, RoundingMode.HALF_UP));

        trasactionAlert.alert(Tipo.DEPOSITO, cliente.get().getNome(), empresa.get().getCNPJ(), dto.valor(), webhookURL);
        smsAlert.alert(Tipo.DEPOSITO, cliente.get().getNome(), cliente.get().getCPF(), dto.valor(), webhookURL);
    }

    @Override
    public void sacar(TransacaoDto dto) {
        Alert trasactionAlert = new TransactionAlert();
        Alert smsAlert = new SMSAlert();

        Optional<Cliente> cliente = clienteService.findByIdRepository(dto.idCliente());
        Optional<Empresa> empresa = empresaService.findByCNPJ(dto.cnpj());

        if (!cliente.isPresent() && !empresa.isPresent()) {
            throw new ValidacaoException("CPF ou CNPJ inválidos");
        }

        String taxa = taxaService.findByIdEmpresa(empresa.get().getId()).get().getTaxa();

        validarValor(dto.valor());
        BigDecimal valorCorrigido = taxarValor(dto.valor(), taxa);
        BigDecimal saldoAtual = empresa.get().getSaldo();

        if (saldoAtual.compareTo(valorCorrigido) > 0) {
            empresa.get().subtrairValorDoSaldo(valorCorrigido);
        } else {
            System.out.println("Valor corrigido: R$ " + valorCorrigido.setScale(2, RoundingMode.HALF_UP));
            System.out.println("Saldo atual: R$ " + saldoAtual.setScale(2, RoundingMode.HALF_UP));
            throw new ValidacaoException("Saldo insuficiente");
        }

        empresaService.saveRepository(empresa.get());

        System.out.println("Valor sacado: R$ " + dto.valor().setScale(2, RoundingMode.HALF_UP));
        System.out.println("Valor do saque com a taxa aplicada: R$ " +
                valorCorrigido.setScale(2, RoundingMode.HALF_UP));

        trasactionAlert.alert(Tipo.SACADO, cliente.get().getNome(), empresa.get().getCNPJ(), dto.valor(), webhookURL);
        smsAlert.alert(Tipo.SACADO, cliente.get().getNome(), cliente.get().getCPF(), dto.valor(), webhookURL);
    }

    private void validarValor(BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidacaoException("O valor não pode ser negativo");
        }
    }

    private BigDecimal taxarValor(BigDecimal valor, String taxaInput) {
        var porcentagemTaxa = Double.parseDouble(taxaInput) / 100.0;
        var valorTaxa = valor.doubleValue() * porcentagemTaxa;
        BigDecimal taxa = new BigDecimal(valorTaxa);
        valor = valor.add(taxa);
        return valor;
    }
}

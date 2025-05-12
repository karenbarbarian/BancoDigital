package br.com.cdb.bancodigitaljpa.service;

import br.com.cdb.bancodigitaljpa.entity.Cliente;
import br.com.cdb.bancodigitaljpa.entity.Conta;
import br.com.cdb.bancodigitaljpa.repository.ClienteRepository;
import br.com.cdb.bancodigitaljpa.repository.ContaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ContaService {
    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public Conta criarConta(Long clienteId, String tipo) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));

        Conta conta = new Conta();
        conta.setCliente(cliente);
        conta.setTipo(tipo);
        return contaRepository.save(conta);
    }

    public Conta buscarConta(Long id) {
        return contaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Conta não encontrada"));
    }

    public Conta realizarDeposito(Long id, BigDecimal valor) {
        Conta conta = buscarConta(id);
        conta.setSaldo(conta.getSaldo().add(valor));
        return contaRepository.save(conta);
    }

    public Conta realizarSaque(Long id, BigDecimal valor) {
        Conta conta = buscarConta(id);
        if (conta.getSaldo().compareTo(valor) < 0)
            throw new RuntimeException("Saldo insuficiente");
        conta.setSaldo(conta.getSaldo().subtract(valor));
        return contaRepository.save(conta);
    }

    public void transferir(Long origemId, Long destinoId, BigDecimal valor) {
        Conta origem = buscarConta(origemId);
        Conta destino = buscarConta(destinoId);

        if (origem.getSaldo().compareTo(valor) < 0)
            throw new RuntimeException("Saldo insuficiente para transferência");

        origem.setSaldo(origem.getSaldo().subtract(valor));
        destino.setSaldo(destino.getSaldo().add(valor));

        contaRepository.save(origem);
        contaRepository.save(destino);
    }

    public Conta aplicarManutencao(Long id, BigDecimal taxa) {
        Conta conta = buscarConta(id);
        if (!conta.getTipo().equalsIgnoreCase("CORRENTE"))
            throw new RuntimeException("Taxa de manutenção só é aplicada a conta corrente");

        conta.setSaldo(conta.getSaldo().subtract(taxa));
        return contaRepository.save(conta);
    }

    public Conta aplicarRendimento(Long id, BigDecimal taxa) {
        Conta conta = buscarConta(id);
        if (!conta.getTipo().equalsIgnoreCase("POUPANCA"))
            throw new RuntimeException("Rendimento só é aplicado a conta poupança");

        BigDecimal rendimento = conta.getSaldo().multiply(taxa);
        conta.setSaldo(conta.getSaldo().add(rendimento));
        return contaRepository.save(conta);
    }
}

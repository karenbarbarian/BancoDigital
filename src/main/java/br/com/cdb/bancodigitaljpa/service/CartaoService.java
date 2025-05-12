package br.com.cdb.bancodigitaljpa.service;

import br.com.cdb.bancodigitaljpa.entity.Cartao;
import br.com.cdb.bancodigitaljpa.entity.Conta;
import br.com.cdb.bancodigitaljpa.repository.CartaoRepository;
import br.com.cdb.bancodigitaljpa.repository.ContaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CartaoService {

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private ContaRepository contaRepository;

    public Cartao emitirCartao(Long contaId, String tipo, BigDecimal limite) {
        Conta conta = contaRepository.findById(contaId)
                .orElseThrow(() -> new EntityNotFoundException("Conta não encontrada"));

        Cartao cartao = new Cartao();
        cartao.setConta(conta);
        cartao.setTipo(tipo);
        cartao.setLimite(limite);
        cartao.setSenha("1234"); // senha padrão
        return cartaoRepository.save(cartao);
    }

    public Cartao buscarCartao(Long id) {
        return cartaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cartão não encontrado"));
    }

    public Cartao realizarPagamento(Long id, BigDecimal valor) {
        Cartao cartao = buscarCartao(id);
        if (!cartao.isAtivo())
            throw new RuntimeException("Cartão inativo");

        if (cartao.getTipo().equalsIgnoreCase("CREDITO")) {
            if (cartao.getFatura().add(valor).compareTo(cartao.getLimite()) > 0)
                throw new RuntimeException("Limite de crédito excedido");

            cartao.setFatura(cartao.getFatura().add(valor));
        } else {
            Conta conta = cartao.getConta();
            if (conta.getSaldo().compareTo(valor) < 0)
                throw new RuntimeException("Saldo insuficiente");
            conta.setSaldo(conta.getSaldo().subtract(valor));
        }
        return cartaoRepository.save(cartao);
    }

    public Cartao alterarLimite(Long id, BigDecimal novoLimite) {
        Cartao cartao = buscarCartao(id);
        cartao.setLimite(novoLimite);
        return cartaoRepository.save(cartao);
    }

    public Cartao alterarStatus(Long id, boolean ativo) {
        Cartao cartao = buscarCartao(id);
        cartao.setAtivo(ativo);
        return cartaoRepository.save(cartao);
    }

    public Cartao alterarSenha(Long id, String novaSenha) {
        Cartao cartao = buscarCartao(id);
        cartao.setSenha(novaSenha);
        return cartaoRepository.save(cartao);
    }

    public BigDecimal consultarFatura(Long id) {
        Cartao cartao = buscarCartao(id);
        return cartao.getFatura();
    }

    public Cartao pagarFatura(Long id, BigDecimal valor) {
        Cartao cartao = buscarCartao(id);
        if (cartao.getFatura().compareTo(valor) > 0)
            cartao.setFatura(cartao.getFatura().subtract(valor));
        else
            cartao.setFatura(BigDecimal.ZERO);
        return cartaoRepository.save(cartao);
    }

    public Cartao alterarLimiteDiario(Long id, BigDecimal novoLimiteDiario) {
        Cartao cartao = buscarCartao(id);
        cartao.setLimiteDiario(novoLimiteDiario);
        return cartaoRepository.save(cartao);
    }
}


package br.com.cdb.bancodigitaljpa.controller;

import br.com.cdb.bancodigitaljpa.entity.Cartao;
import br.com.cdb.bancodigitaljpa.service.CartaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    @Autowired
    private CartaoService cartaoService;

    @PostMapping
    public ResponseEntity<Cartao> emitir(@RequestParam Long contaId, @RequestParam String tipo, @RequestParam BigDecimal limite) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartaoService.emitirCartao(contaId, tipo, limite));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cartao> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(cartaoService.buscarCartao(id));
    }

    @PostMapping("/{id}/pagamento")
    public ResponseEntity<Cartao> pagar(@PathVariable Long id, @RequestParam BigDecimal valor) {
        return ResponseEntity.ok(cartaoService.realizarPagamento(id, valor));
    }

    @PutMapping("/{id}/limite")
    public ResponseEntity<Cartao> alterarLimite(@PathVariable Long id, @RequestParam BigDecimal novoLimite) {
        return ResponseEntity.ok(cartaoService.alterarLimite(id, novoLimite));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Cartao> alterarStatus(@PathVariable Long id, @RequestParam boolean ativo) {
        return ResponseEntity.ok(cartaoService.alterarStatus(id, ativo));
    }

    @PutMapping("/{id}/senha")
    public ResponseEntity<Cartao> alterarSenha(@PathVariable Long id, @RequestParam String novaSenha) {
        return ResponseEntity.ok(cartaoService.alterarSenha(id, novaSenha));
    }

    @GetMapping("/{id}/fatura")
    public ResponseEntity<BigDecimal> consultarFatura(@PathVariable Long id) {
        return ResponseEntity.ok(cartaoService.consultarFatura(id));
    }

    @PostMapping("/{id}/fatura/pagamento")
    public ResponseEntity<Cartao> pagarFatura(@PathVariable Long id, @RequestParam BigDecimal valor) {
        return ResponseEntity.ok(cartaoService.pagarFatura(id, valor));
    }

    @PutMapping("/{id}/limite-diario")
    public ResponseEntity<Cartao> alterarLimiteDiario(@PathVariable Long id, @RequestParam BigDecimal novoLimiteDiario) {
        return ResponseEntity.ok(cartaoService.alterarLimiteDiario(id, novoLimiteDiario));
    }
}

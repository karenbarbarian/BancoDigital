package br.com.cdb.bancodigitaljpa.controller;

import br.com.cdb.bancodigitaljpa.entity.Conta;
import br.com.cdb.bancodigitaljpa.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/contas")
public class ContaController {

    @Autowired
    private ContaService contaService;

    @PostMapping
    public ResponseEntity<Conta> criarConta(@RequestParam Long clienteId, @RequestParam String tipo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(contaService.criarConta(clienteId, tipo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Conta> obterConta(@PathVariable Long id) {
        return ResponseEntity.ok(contaService.buscarConta(id));
    }

    @GetMapping("/{id}/saldo")
    public ResponseEntity<BigDecimal> consultarSaldo(@PathVariable Long id) {
        return ResponseEntity.ok(contaService.buscarConta(id).getSaldo());
    }

    @PostMapping("/{id}/deposito")
    public ResponseEntity<Conta> deposito(@PathVariable Long id, @RequestParam BigDecimal valor) {
        return ResponseEntity.ok(contaService.realizarDeposito(id, valor));
    }

    @PostMapping("/{id}/saque")
    public ResponseEntity<Conta> saque(@PathVariable Long id, @RequestParam BigDecimal valor) {
        return ResponseEntity.ok(contaService.realizarSaque(id, valor));
    }

    @PostMapping("/{id}/pix")
    public ResponseEntity<String> pagamentoPix(@PathVariable Long id, @RequestParam BigDecimal valor) {
        contaService.realizarSaque(id, valor); // Simples simulação
        return ResponseEntity.ok("Pagamento Pix realizado com sucesso");
    }

    @PostMapping("/{id}/transferencia")
    public ResponseEntity<String> transferencia(@PathVariable Long id, @RequestParam Long destinoId, @RequestParam BigDecimal valor) {
        contaService.transferir(id, destinoId, valor);
        return ResponseEntity.ok("Transferência realizada com sucesso");
    }

    @PutMapping("/{id}/manutencao")
    public ResponseEntity<Conta> aplicarTaxa(@PathVariable Long id, @RequestParam BigDecimal taxa) {
        return ResponseEntity.ok(contaService.aplicarManutencao(id, taxa));
    }

    @PutMapping("/{id}/rendimentos")
    public ResponseEntity<Conta> aplicarRendimentos(@PathVariable Long id, @RequestParam BigDecimal taxa) {
        return ResponseEntity.ok(contaService.aplicarRendimento(id, taxa));
    }
}


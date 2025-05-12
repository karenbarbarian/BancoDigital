package br.com.cdb.bancodigitaljpa.controller;

import br.com.cdb.bancodigitaljpa.entity.Seguro;
import br.com.cdb.bancodigitaljpa.service.SeguroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/seguros")
public class SeguroController {

    @Autowired
    private SeguroService seguroService;

    @PostMapping
    public ResponseEntity<Seguro> contratarSeguro(
            @RequestParam Long clienteId,
            @RequestParam String tipo,
            @RequestParam BigDecimal valorMensal) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(seguroService.contratar(clienteId, tipo, valorMensal));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Seguro> obterSeguro(@PathVariable Long id) {
        return ResponseEntity.ok(seguroService.obterPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<Seguro>> listarSeguros() {
        return ResponseEntity.ok(seguroService.listarTodos());
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<Seguro> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(seguroService.cancelar(id));
    }

}


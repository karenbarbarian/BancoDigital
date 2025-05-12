package br.com.cdb.bancodigitaljpa.service;

import br.com.cdb.bancodigitaljpa.entity.Cliente;
import br.com.cdb.bancodigitaljpa.entity.Seguro;
import br.com.cdb.bancodigitaljpa.repository.ClienteRepository;
import br.com.cdb.bancodigitaljpa.repository.SeguroRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class SeguroService {

    @Autowired
    private SeguroRepository seguroRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public Seguro contratar(Long clienteId, String tipo, BigDecimal valorMensal) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));

        Seguro seguro = new Seguro();
        seguro.setCliente(cliente);
        seguro.setTipo(tipo);
        seguro.setValorMensal(valorMensal);
        return seguroRepository.save(seguro);
    }

    public Seguro obterPorId(Long id) {
        return seguroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Seguro não encontrado"));
    }

    public List<Seguro> listarTodos() {
        return seguroRepository.findAll();
    }

    public Seguro cancelar(Long id) {
        Seguro seguro = obterPorId(id);
        seguro.setAtivo(false);
        return seguroRepository.save(seguro);
    }
}

package br.com.cdb.bancodigitaljpa.repository;

import br.com.cdb.bancodigitaljpa.entity.Seguro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeguroRepository extends JpaRepository<Seguro, Long> {
    List<Seguro> findByClienteId(Long clienteId);
}


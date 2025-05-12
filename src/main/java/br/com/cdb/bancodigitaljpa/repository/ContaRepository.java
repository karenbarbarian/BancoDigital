package br.com.cdb.bancodigitaljpa.repository;

import br.com.cdb.bancodigitaljpa.entity.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaRepository extends JpaRepository<Conta, Long> {
}

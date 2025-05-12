package br.com.cdb.bancodigitaljpa.repository;

import br.com.cdb.bancodigitaljpa.entity.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartaoRepository extends JpaRepository<Cartao, Long> {
}
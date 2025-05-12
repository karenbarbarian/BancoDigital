package br.com.cdb.bancodigitaljpa.repository;

import br.com.cdb.bancodigitaljpa.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ClienteRepository extends JpaRepository<Cliente,Long> {

}

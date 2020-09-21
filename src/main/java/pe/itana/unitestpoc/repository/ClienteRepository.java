package pe.itana.unitestpoc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pe.itana.unitestpoc.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

}

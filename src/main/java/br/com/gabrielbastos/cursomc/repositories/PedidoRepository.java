package br.com.gabrielbastos.cursomc.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gabrielbastos.cursomc.domain.Cliente;
import br.com.gabrielbastos.cursomc.domain.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

	Page<Pedido> findByCliente(Cliente cliente, Pageable pageRequest);

}

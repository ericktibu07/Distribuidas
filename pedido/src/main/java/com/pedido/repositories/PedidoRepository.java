package com.pedido.repositories;

import com.pedido.models.entities.Pedido;
import org.springframework.data.repository.CrudRepository;

public interface PedidoRepository extends CrudRepository<Pedido, Long> {

}
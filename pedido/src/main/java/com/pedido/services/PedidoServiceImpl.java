package com.pedido.services;

import com.pedido.models.entities.Pedido;
import com.pedido.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository repository;

    @Override
    public List<Pedido> findAll() {
        return (List<Pedido>) repository.findAll();
    }

    @Override
    public Optional<Pedido> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Pedido save(Pedido pedido) {
        return repository.save(pedido);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
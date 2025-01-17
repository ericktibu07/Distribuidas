package com.pedido.controllers;

import com.pedido.dto.ErrorResponse;
import com.pedido.dto.ValidationError;
import com.pedido.models.entities.Pedido;
import com.pedido.services.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Pedido pedido, BindingResult result) {
        // Agregar logs para debugging
        System.out.println("Pedido recibido: " + pedido);
        System.out.println("Número de pedido: " + pedido.getNumeroPedido());
        System.out.println("Cliente: " + pedido.getCliente());
        System.out.println("Fecha: " + pedido.getFechaPedido());
        System.out.println("Total: " + pedido.getTotal());

        if(result.hasErrors()) {
            return validar(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(pedido));
    }

    @GetMapping
    public List<Pedido> listar() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Optional<Pedido> pedidoOptional = service.findById(id);
        if (pedidoOptional.isPresent()) {
            return ResponseEntity.ok().body(pedidoOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Pedido pedido, BindingResult result, @PathVariable Long id) {
        if(result.hasErrors()) {
            return validar(result);
        }

        Optional<Pedido> pedidoOptional = service.findById(id);
        if (pedidoOptional.isPresent()) {
            Pedido pedidoDB = pedidoOptional.get();
            pedidoDB.setNumeroPedido(pedido.getNumeroPedido());
            pedidoDB.setCliente(pedido.getCliente());
            pedidoDB.setFechaPedido(pedido.getFechaPedido());
            pedidoDB.setTotal(pedido.getTotal());

            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(pedidoDB));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Pedido> pedidoOptional = service.findById(id);
        if (pedidoOptional.isPresent()) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private ResponseEntity<?> validar(BindingResult result) {
        List<ValidationError> errors = result.getFieldErrors()
                .stream()
                .map(err -> new ValidationError(
                        err.getField(),
                        err.getDefaultMessage()
                ))
                .collect(Collectors.toList());

        ErrorResponse errorResponse = new ErrorResponse(
                "Validation failed",
                errors
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }
}
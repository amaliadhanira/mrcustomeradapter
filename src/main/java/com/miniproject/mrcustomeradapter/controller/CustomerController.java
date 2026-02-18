package com.miniproject.mrcustomeradapter.controller;


import com.miniproject.mrcustomeradapter.entity.Customer;
import com.miniproject.mrcustomeradapter.service.CustomerService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @PostMapping("/inputCustomer")
    public Mono<Customer> create(@RequestBody Customer customer) {
        return service.create(customer);
    }

    @PostMapping("/login")
    public Mono<Customer> loginCustomer(@RequestBody Customer customer){
        return service.login(customer.getEmail(), customer.getPassword());
    }

    @PostMapping("/editCustomer/{id}")
    public Mono<Customer> editCustomer(@PathVariable UUID id, @RequestBody Customer customer){
        return service.edit(id, customer);
    }

    @GetMapping("/allCustomer")
    public Flux<Customer> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Customer> getById(@PathVariable UUID id) {
        return service.findById(id);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable UUID id) {
        return service.delete(id);
    }

}

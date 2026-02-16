package com.miniproject.mrcustomeradapter.service;

import com.miniproject.mrcustomeradapter.entity.Customer;
import com.miniproject.mrcustomeradapter.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public Mono<Customer> create(Customer customer) {
        customer.setId(UUID.randomUUID());
        return repository.save(customer);
    }

    public Flux<Customer> findAll() {
        return repository.findAll();
    }

    public Mono<Customer> findById(UUID id) {
        return repository.findById(id);
    }

    public Mono<Void> delete(UUID id) {
        return repository.deleteById(id);
    }
}

package com.miniproject.mrcustomeradapter.repository;

import com.miniproject.mrcustomeradapter.entity.Customer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface CustomerRepository extends ReactiveCrudRepository<Customer, UUID> {

    Mono<Customer> findByEmail(String email);
}

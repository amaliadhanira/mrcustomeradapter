package com.miniproject.mrcustomeradapter.service;

import com.miniproject.mrcustomeradapter.entity.Customer;
import com.miniproject.mrcustomeradapter.repository.CustomerRepository;
import com.miniproject.mrcustomeradapter.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.data.relational.core.query.Update;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class CustomerService {

    @Autowired
    private R2dbcEntityTemplate template;

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public Mono<Customer> create(Customer customer) {
        customer.setId(UUID.randomUUID());

        String hashedPassword = PasswordUtil.encrypt(customer.getPassword());
        customer.setPassword(hashedPassword);

        return template.insert(Customer.class)
                .using(customer);
    }

    public Mono<Customer> login(String email, String rawPassword) {
        return repository.findByEmail(email)
                .filter(user -> PasswordUtil.matches(rawPassword, user.getPassword()));
    }

    public Mono<Customer> edit(UUID id, Customer customer){

        String hashedPassword = PasswordUtil.encrypt(customer.getPassword());
        customer.setPassword(hashedPassword);
        customer.setId(id);

        return template.update(Customer.class)
                .matching(Query.query(Criteria.where("id").is(id)))
                .apply(Update.update("email", customer.getEmail())
                        .set("user_password", customer.getPassword())
                        .set("profile_pict", customer.getProfilePict()))
                .flatMap(rowsUpdated -> {
                    if (rowsUpdated > 0) {
                        return template.selectOne(
                                Query.query(Criteria.where("id").is(id)),
                                Customer.class
                        );
                    } else {
                        return Mono.error(new RuntimeException("Customer is not found"));
                    }
                });
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

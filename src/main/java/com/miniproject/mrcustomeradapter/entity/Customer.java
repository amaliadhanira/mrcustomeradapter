package com.miniproject.mrcustomeradapter.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Data
@Table("mr_customer")
public class Customer {
    @Id
    private UUID id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}

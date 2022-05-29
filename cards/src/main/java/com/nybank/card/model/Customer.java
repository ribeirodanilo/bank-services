package com.nybank.card.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter @Getter @NoArgsConstructor @ToString
public class Customer {

    private Long customerId;
    private String name;
    private String email;
    private String mobileNumber;
    private LocalDateTime createAt;

    public Customer(String name, String email, String mobileNumber, LocalDateTime createAt) {
        this.name = name;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.createAt = createAt;
    }
}

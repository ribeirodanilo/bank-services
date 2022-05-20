package com.mybank.customer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Setter @Getter @NoArgsConstructor @ToString
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

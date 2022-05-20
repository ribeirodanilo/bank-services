package com.mybank.accounts;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter @Setter @ToString
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long accountId;
    private Long customerId;
    private String accountType;
    private String branchAddress;
    private LocalDateTime createAt;

    public Account(Long customerId, String accountType, String branchAddress, LocalDateTime createAt) {
        this.customerId = customerId;
        this.accountType = accountType;
        this.branchAddress = branchAddress;
        this.createAt = createAt;
    }
}

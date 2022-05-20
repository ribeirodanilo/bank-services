package com.mybank.accounts;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class AccountService {

    private AccountRepository accountRepository;

    public Account saveAccount(Account account) {
        log.info("Saving new Account {} to the database", account.getBranchAddress());
        return accountRepository.save(account);
    }

    public Optional<Account> findByCustomerId(Long customerId) {
        return accountRepository.findByCustomerId(customerId);
    }


}

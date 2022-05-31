package com.mybank.accounts;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mybank.accounts.client.CardsFeignClient;
import com.mybank.customer.Customer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mybank.model.Card;
import com.mybank.model.CustomerDetails;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("api/v1/accounts")
@AllArgsConstructor
@Slf4j
public class AccountController {

    private final AccountService accountService;

    private final AccountServiceConfig accountConf;

    private final CardsFeignClient cardsFeignClient;

    @PostMapping("/myAccount")
    public Account getAccountDetails(@RequestBody Customer customer) {
        return accountService.findByCustomerId(customer.getCustomerId()).orElse(null);
    }

    @GetMapping("/properties")
    public String getPropertyDetails() throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        Properties properties = new Properties(accountConf.getMsg(), accountConf.getBuildVersion(), accountConf.getMailDetails(), accountConf.getActiveBranches());
        return ow.writeValueAsString(properties);
    }

    @PostMapping("/myCustomerDetails")
    @CircuitBreaker(name = "detailsForCustomerSupportApp", fallbackMethod = "myCustomerDetailsFallBack")
    //@Retry(name = "retryForCustomerDetails", fallbackMethod = "myCustomerDetailsFallBack")
    public CustomerDetails myCustomerDetails(@RequestHeader("mybank-correlation-id") String correlationId,
                                             @RequestBody Customer customer) {

        log.info("myCustomerDetails() method started");
        Account account = accountService.findByCustomerId(customer.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("Account not found for customerId {%s}" + customer.getCustomerId())));

        List<Card> cards = cardsFeignClient.getCardDetails(correlationId, customer);
        CustomerDetails customerDetails = new CustomerDetails(account, cards);
        log.info("myCustomerDetails() method ended");
        return customerDetails;

    }

    public CustomerDetails myCustomerDetailsFallBack(String correlationId, Customer customer, Throwable t) {
        Account account = accountService.findByCustomerId(customer.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("Account not found for customerId {%s}" + customer.getCustomerId())));

        // List<Card> cards = cardsFeignClient.getCardDetails(customer);
        CustomerDetails customerDetails = new CustomerDetails(account, null);

        return customerDetails;
    }

    @GetMapping("/sayHello")
    @RateLimiter(name = "sayHello", fallbackMethod = "sayHelloFallback")
    public String sayHello() {
        System.out.println("say hello " + LocalDateTime.now());
        return "Hello, Welcome to MyBank";
    }

    public String sayHelloFallback(Throwable throwable) {
        System.out.println("say hi "+ LocalDateTime.now());
        return "Hi, Welcome to MyBank";
    }

}

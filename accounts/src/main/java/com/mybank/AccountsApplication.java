package com.mybank;

import com.mybank.accounts.Account;
import com.mybank.accounts.AccountService;
import com.mybank.customer.Customer;
import com.mybank.customer.CustomerService;
import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
@RefreshScope
@EnableFeignClients
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

	@Bean
	public TimedAspect timedAspect(MeterRegistry registry) {
		return new TimedAspect(registry);
	}

	@Bean
	CommandLineRunner run(AccountService accountService, CustomerService customerService) {
		return args -> {


			Customer customer = new Customer(
					"Danilo",
					"ribeiro.danilo@gmail.com",
					"11930043153",
					LocalDateTime.now());

			customer = customerService.saveCustomer(customer);

			Account account = new Account(
					customer.getCustomerId(),
					"Savings",
					"132 Main Street, New York",
					LocalDateTime.now());


			accountService.saveAccount(account);

		};
	}

}

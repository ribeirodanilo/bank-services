package com.nybank.card;

import com.nybank.card.model.Card;
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
public class CardsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CardsApplication.class, args);
    }

    @Bean
    CommandLineRunner run(CardRepository cardRepository) {
        return args -> {

            Card card = new Card(
                    1L,
                    "1234",
                    "Master",
                    10_000,
                    1_000,
                    8000,
                    LocalDateTime.now());

            cardRepository.save(card);

            card = new Card(
                    1L,
                    "5678",
                    "Master",
                    20_000,
                    2_000,
                    4000,
                    LocalDateTime.now());

            cardRepository.save(card);

        };
    }
}

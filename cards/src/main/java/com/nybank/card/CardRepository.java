package com.nybank.card;

import java.util.List;

import com.nybank.card.model.Card;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends CrudRepository<Card, Long> {

    List<Card> findByCustomerId(Long customerId);

}

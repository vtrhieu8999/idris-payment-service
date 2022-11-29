package com.example.demo.repo;

import com.example.demo.model.product.Card;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepo extends JpaRepository<Card, Integer> {
    int countByCardConfigIdAndTransIdIsNull(Long cardConfigId);
    List<Card> findCardsByCardConfigIdAndTransIdIsNull(Long cardConfigId, Pageable pageable);
    default List<Card> getAvailableCardList(long cardConfigId, int amount){
        return findCardsByCardConfigIdAndTransIdIsNull(cardConfigId, PageRequest.of(0, amount));
    }
}

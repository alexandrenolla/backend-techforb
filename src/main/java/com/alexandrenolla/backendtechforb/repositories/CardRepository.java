package com.alexandrenolla.backendtechforb.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alexandrenolla.backendtechforb.models.Card;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    List<Card> findAll();

    List<Card> findByUser_Id(Long id);
}

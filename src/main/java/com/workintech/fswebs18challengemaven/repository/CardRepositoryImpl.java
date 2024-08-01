package com.workintech.fswebs18challengemaven.repository;

import com.workintech.fswebs18challengemaven.entity.Card;
import com.workintech.fswebs18challengemaven.entity.Color;
import com.workintech.fswebs18challengemaven.entity.Type;
import com.workintech.fswebs18challengemaven.exceptions.CardException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class CardRepositoryImpl implements CardRepository{

    private final EntityManager entityManager;

    @Autowired
    public CardRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    @Transactional
    public Card save(Card card) {
        entityManager.persist(card);
        return card;
    }

    @Override
    public List<Card> findByColor(String color) {
        Color c = Color.valueOf(color);
        TypedQuery<Card> query = entityManager.createQuery("SELECT c FROM Card c WHERE c.color = :color", Card.class);
        query.setParameter("color", c);
        return query.getResultList();
    }

    @Override
    public List<Card> findAll() {
        TypedQuery<Card> query = entityManager.createQuery("SELECT c FROM Card c", Card.class);
        return query.getResultList();
    }

    @Override
    public List<Card> findByValue(Integer value) {
        TypedQuery<Card> query = entityManager.createQuery("SELECT c FROM Card c WHERE c.value = :value", Card.class);
        query.setParameter("value", value);
        return query.getResultList();
    }

    @Override
    public List<Card> findByType(String type) {
        Type t = Type.valueOf(type);
        TypedQuery<Card> query = entityManager.createQuery("SELECT t from Card t WHERE t.type = :type", Card.class);
        query.setParameter("type", t);
        return query.getResultList();
    }

    @Override
    @Transactional
    public Card update(Card card) {
        return entityManager.merge(card);
    }

    @Override
    @Transactional
    public Card remove(long id) {
        Card card = entityManager.find(Card.class, id);
        if(card == null){
            throw new CardException("Card could not found: " + id, HttpStatus.NOT_FOUND);
        }
        entityManager.remove(card);
        return card;
    }

}

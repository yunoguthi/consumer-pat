package br.com.alelo.consumer.consumerpat.service;

import br.com.alelo.consumer.consumerpat.entity.Card;
import br.com.alelo.consumer.consumerpat.entity.enums.ECardType;
import br.com.alelo.consumer.consumerpat.exception.RecordNotFoundException;
import br.com.alelo.consumer.consumerpat.respository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CardService implements CrudService<Card, Integer> {

    @Autowired
    private CardRepository repository;

    @Override
    public Card create(Card entity) throws RecordNotFoundException {
        return save(entity);
    }

    @Override
    public void delete(Integer id) throws RecordNotFoundException {
        Card Card = findByIdOrThrowException(id);
        repository.delete(Card);
    }

    @Override
    public Card update(Card entity) throws RecordNotFoundException {
        findByIdOrThrowException(entity.getId());
        return save(entity);
    }

    @Override
    public Card read(Integer id) throws RecordNotFoundException {
        return findByIdOrThrowException(id);
    }

    @Override
    public Page<Card> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Iterable<Card> getAll() {
        return repository.findAll();
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public Card save(Card entity) throws RecordNotFoundException {
        return repository.save(entity);
    }

    public Card adicionarSaldo(String cardNumber, double value) throws RecordNotFoundException {
        Card card = findByCardNumberOrThrowException(cardNumber);
        card.setCardBalance(card.getCardBalance().add(new BigDecimal(value)));
        return save(card);
    }

    public Card adicionarSaldoId(Integer id, double value) throws RecordNotFoundException {
        Card card = findByIdOrThrowException(id);
        card.setCardBalance(card.getCardBalance().add(new BigDecimal(value)));
        return save(card);
    }

    public Card findByCardNumber(String cardNumber) throws RecordNotFoundException {
        return findByCardNumberOrThrowException(cardNumber);
    }

    public Card findById(Integer id) throws RecordNotFoundException {
        return findByIdOrThrowException(id);
    }

    public Card findByCardNumberAndCardType(String cardNumber, ECardType cardType) throws RecordNotFoundException {
        return findByCardNumberAndCardTypeOrThrowException(cardNumber, cardType);
    }

    private Card findByIdOrThrowException(Integer id) throws RecordNotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Registro nao encontrado com o id " + id));
    }

    private Card findByCardNumberOrThrowException(String cardNumber) throws RecordNotFoundException {
        return repository.findByCardNumber(cardNumber)
                .orElseThrow(() -> new RecordNotFoundException("Registro nao encontrado com o card number " + cardNumber));
    }

    private Card findByCardNumberAndCardTypeOrThrowException(String cardNumber, ECardType cardType) throws RecordNotFoundException {
        return repository.findByCardNumberAndCardType(cardNumber, cardType)
                .orElseThrow(() -> new RecordNotFoundException("Registro nao encontrado com o card number " + cardNumber + " e card type " + cardType));
    }

}

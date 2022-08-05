package br.com.alelo.consumer.consumerpat.service;

import br.com.alelo.consumer.consumerpat.entity.Card;
import br.com.alelo.consumer.consumerpat.entity.Establishment;
import br.com.alelo.consumer.consumerpat.entity.Extract;
import br.com.alelo.consumer.consumerpat.entity.Product;
import br.com.alelo.consumer.consumerpat.entity.enums.ECardType;
import br.com.alelo.consumer.consumerpat.exception.PurchaseNotAllowedException;
import br.com.alelo.consumer.consumerpat.exception.RecordNotFoundException;
import br.com.alelo.consumer.consumerpat.respository.ExtractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class ExtractService implements CrudService<Extract, Integer>{


    @Autowired
    private ExtractRepository repository;

    @Autowired
    private EstablishmentService establishmentService;

    @Autowired
    private CardService cardService;

    @Autowired
    private ProductService productService;

    @Override
    public Extract create(Extract entity) throws RecordNotFoundException {
        return save(entity);
    }

    @Override
    public void delete(Integer id) throws RecordNotFoundException {
        Extract extract = findByIdOrThrowException(id);
        repository.delete(extract);
    }

    @Override
    public Extract update(Extract entity) throws RecordNotFoundException {
        findByIdOrThrowException(entity.getId());
        return save(entity);
    }

    @Override
    public Extract read(Integer id) throws RecordNotFoundException {
        return findByIdOrThrowException(id);
    }

    @Override
    public Page<Extract> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Iterable<Extract> getAll() {
        return repository.findAll();
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public Extract save(Extract entity) throws RecordNotFoundException {
        return repository.save(entity);
    }

    public Extract buy(ECardType cardType, String establishmentName, String cardNumber, String productDescription, double value) throws RecordNotFoundException, PurchaseNotAllowedException {
        Establishment establishment = establishmentService.findByName(establishmentName);
        Card card = cardService.findByCardNumberAndCardType(cardNumber, cardType);

        if (!establishment.getEstablishmentType().equals(cardType)) {
            throw new PurchaseNotAllowedException(cardType + " not allowed for this purchase");
        }

        Product product = productService.findByDescription(productDescription);
        Extract extract = new Extract();
        extract.setDateBuy(new Date());
        extract.setEstablishment(establishment);
        extract.setCardNumber(card);
        extract.setValue(aplicaDesconto(cardType, value));
        return save(extract);
    }

    public Extract buyId(Integer establishmentId, Integer cardId, Integer productId, double value) throws RecordNotFoundException, PurchaseNotAllowedException {
        Establishment establishment = establishmentService.findById(establishmentId);
        Card card = cardService.findById(cardId);

        if (!establishment.getEstablishmentType().equals(card.getCardType())) {
            throw new PurchaseNotAllowedException(card.getCardType() + " not allowed for this purchase");
        }

        Product product = productService.findById(productId);
        Extract extract = new Extract();
        extract.setDateBuy(new Date());
        extract.setEstablishment(establishment);
        extract.setCardNumber(card);
        extract.setValue(aplicaDesconto(card.getCardType(), value));
        return save(extract);
    }

    private BigDecimal aplicaDesconto(ECardType cardType, double value) {
        if (cardType.equals(ECardType.FOOD)) {
            return new BigDecimal(value * 0.9);
        }
        if (cardType.equals(ECardType.FUEL)) {
            return new BigDecimal(value * 1.35);
        }
        return new BigDecimal(value);
    }

    private Extract findByIdOrThrowException(Integer id) throws RecordNotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Registro nao encontrado com o id " + id));
    }

}

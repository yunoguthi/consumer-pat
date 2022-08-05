package br.com.alelo.consumer.consumerpat.controller;

import br.com.alelo.consumer.consumerpat.entity.Card;
import br.com.alelo.consumer.consumerpat.exception.RecordNotFoundException;
import br.com.alelo.consumer.consumerpat.service.CardService;
import br.com.alelo.consumer.consumerpat.util.DirectionConverter;
import br.com.alelo.consumer.consumerpat.util.RestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/card")
public class CardController {

    private final CardService cardService;

    @Autowired
    public CardController(CardService cardService
                              ){
        this.cardService = cardService;
    }

    @GetMapping
    public ResponseEntity<Page<Card>> list(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                  @RequestParam(value = "size", defaultValue = "500") int size,
                                                                  @RequestParam(value = "direction", defaultValue = "asc") String direction,
                                                                  @RequestParam(value = "sortBy", defaultValue = "name") String sortBy)
            throws RecordNotFoundException {
        Pageable pageable = PageRequest.of(page, size, DirectionConverter.from(direction), sortBy);
        Page<Card> pageResult = cardService.findAll(pageable);
        return ResponseEntity.ok(pageResult);
    }

    @PostMapping
    public ResponseEntity<Card> create(@RequestBody Card card) throws RecordNotFoundException {
        return RestUtil.createWithLocation(getClass(), cardService.create(card));
    }

    @PutMapping
    public ResponseEntity<Card> update(@RequestBody Card card) throws RecordNotFoundException {
        return ResponseEntity.ok(cardService.update(card));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Card> read(@PathVariable("id") Integer id) throws RecordNotFoundException {
        return ResponseEntity.ok(cardService.read(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) throws RecordNotFoundException {
        cardService.delete(id);
        return ResponseEntity.ok().build();
    }

    /*
     * Deve creditar(adicionar) um valor(value) em um no cartão.
     * Para isso ele precisa indenficar qual o cartão correto a ser recarregado,
     * para isso deve usar o número do cartão(cardNumber) fornecido.
     */
    @PutMapping(value = "/setcardbalance")
    public ResponseEntity<Card> setBalance(@RequestParam("cardNumber") String cardNumber, @RequestParam("value") double value) throws RecordNotFoundException {
        return ResponseEntity.ok(cardService.adicionarSaldo(cardNumber, value));
    }

    /*
     * Deve creditar(adicionar) um valor(value) em um no cartão.
     * Para isso ele precisa indenficar qual o cartão correto a ser recarregado,
     * para isso deve usar o número do cartão(cardNumber) fornecido.
     */
    @PutMapping(value = "/setcardbalanceid")
    public ResponseEntity<Card> setBalanceId(@RequestParam("cardNumber") Integer id, @RequestParam("cardNumber") double value) throws RecordNotFoundException {
        return ResponseEntity.ok(cardService.adicionarSaldoId(id, value));
    }

}

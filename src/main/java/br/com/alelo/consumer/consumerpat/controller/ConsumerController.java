package br.com.alelo.consumer.consumerpat.controller;

import br.com.alelo.consumer.consumerpat.entity.Card;
import br.com.alelo.consumer.consumerpat.entity.Consumer;
import br.com.alelo.consumer.consumerpat.entity.enums.ECardType;
import br.com.alelo.consumer.consumerpat.exception.RecordNotFoundException;
import br.com.alelo.consumer.consumerpat.service.CardService;
import br.com.alelo.consumer.consumerpat.service.ConsumerService;
import br.com.alelo.consumer.consumerpat.service.ExtractService;
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
@RequestMapping("/consumer")
public class ConsumerController {

    private final ConsumerService consumerService;

    private final ExtractService extractService;

    private final CardService cardService;

    @Autowired
    public ConsumerController(ConsumerService consumerService,
                              CardService cardService,
                              ExtractService extractService
                              ){
        this.consumerService = consumerService;
        this.cardService = cardService;
        this.extractService = extractService;
    }

    @GetMapping
    public ResponseEntity<Page<Consumer>> list(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                  @RequestParam(value = "size", defaultValue = "500") int size,
                                                                  @RequestParam(value = "direction", defaultValue = "asc") String direction,
                                                                  @RequestParam(value = "sortBy", defaultValue = "name") String sortBy)
            throws RecordNotFoundException {
        Pageable pageable = PageRequest.of(page, size, DirectionConverter.from(direction), sortBy);
        Page<Consumer> pageResult = consumerService.findAll(pageable);
        return ResponseEntity.ok(pageResult);
    }

    @PostMapping
    public ResponseEntity<Consumer> create(@RequestBody Consumer consumer) throws RecordNotFoundException {
        return RestUtil.createWithLocation(getClass(), consumerService.create(consumer));
    }

    @PutMapping
    public ResponseEntity<Consumer> update(@RequestBody Consumer consumer) throws RecordNotFoundException {
        return ResponseEntity.ok(consumerService.update(consumer));
    }

}

package br.com.alelo.consumer.consumerpat.controller;

import br.com.alelo.consumer.consumerpat.entity.Extract;
import br.com.alelo.consumer.consumerpat.entity.enums.ECardType;
import br.com.alelo.consumer.consumerpat.exception.PurchaseNotAllowedException;
import br.com.alelo.consumer.consumerpat.exception.RecordNotFoundException;
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
@RequestMapping("/extract")
public class ExtractController {

    private final ExtractService extractService;

    @Autowired
    public ExtractController(ExtractService extractService
                              ){
        this.extractService = extractService;
    }

    @GetMapping
    public ResponseEntity<Page<Extract>> list(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                  @RequestParam(value = "size", defaultValue = "500") int size,
                                                                  @RequestParam(value = "direction", defaultValue = "asc") String direction,
                                                                  @RequestParam(value = "sortBy", defaultValue = "name") String sortBy)
            throws RecordNotFoundException {
        Pageable pageable = PageRequest.of(page, size, DirectionConverter.from(direction), sortBy);
        Page<Extract> pageResult = extractService.findAll(pageable);
        return ResponseEntity.ok(pageResult);
    }

    @PostMapping
    public ResponseEntity<Extract> create(@RequestBody Extract extract) throws RecordNotFoundException {
        return RestUtil.createWithLocation(getClass(), extractService.create(extract));
    }

    @PutMapping
    public ResponseEntity<Extract> update(@RequestBody Extract extract) throws RecordNotFoundException {
        return ResponseEntity.ok(extractService.update(extract));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Extract> read(@PathVariable("id") Integer id) throws RecordNotFoundException {
        return ResponseEntity.ok(extractService.read(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) throws RecordNotFoundException {
        extractService.delete(id);
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @PostMapping(value = "/buy")
    public void buy(@RequestParam("cardType") ECardType cardType,
                    @RequestParam("establishmentName") String establishmentName,
                    @RequestParam("cardNumber") String cardNumber,
                    @RequestParam("productDescription") String productDescription,
                    @RequestParam("value") double value) throws RecordNotFoundException, PurchaseNotAllowedException {
        extractService.buy(cardType, establishmentName, cardNumber, productDescription, value);
    }

    @ResponseBody
    @PostMapping(value = "/buyId")
    public void buyId(
                    @RequestParam("establishmentId") Integer establishmentId,
                    @RequestParam("cardId") Integer cardId,
                    @RequestParam("productId") Integer productId,
                    @RequestParam("value") double value) throws RecordNotFoundException, PurchaseNotAllowedException {
        extractService.buyId(establishmentId, cardId, productId, value);
    }

}

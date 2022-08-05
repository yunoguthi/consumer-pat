package br.com.alelo.consumer.consumerpat.controller;

import br.com.alelo.consumer.consumerpat.entity.Establishment;
import br.com.alelo.consumer.consumerpat.entity.enums.ECardType;
import br.com.alelo.consumer.consumerpat.exception.RecordNotFoundException;
import br.com.alelo.consumer.consumerpat.service.EstablishmentService;
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
@RequestMapping("/establishment")
public class EstablishmentController {

    private final EstablishmentService establishmentService;

    @Autowired
    public EstablishmentController(EstablishmentService establishmentService
                              ){
        this.establishmentService = establishmentService;
    }

    @GetMapping
    public ResponseEntity<Page<Establishment>> list(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                  @RequestParam(value = "size", defaultValue = "500") int size,
                                                                  @RequestParam(value = "direction", defaultValue = "asc") String direction,
                                                                  @RequestParam(value = "sortBy", defaultValue = "name") String sortBy)
            throws RecordNotFoundException {
        Pageable pageable = PageRequest.of(page, size, DirectionConverter.from(direction), sortBy);
        Page<Establishment> pageResult = establishmentService.findAll(pageable);
        return ResponseEntity.ok(pageResult);
    }

    @PostMapping
    public ResponseEntity<Establishment> create(@RequestBody Establishment establishment) throws RecordNotFoundException {
        return RestUtil.createWithLocation(getClass(), establishmentService.create(establishment));
    }

    @PutMapping
    public ResponseEntity<Establishment> update(@RequestBody Establishment establishment) throws RecordNotFoundException {
        return ResponseEntity.ok(establishmentService.update(establishment));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Establishment> read(@PathVariable("id") Integer id) throws RecordNotFoundException {
        return ResponseEntity.ok(establishmentService.read(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) throws RecordNotFoundException {
        establishmentService.delete(id);
        return ResponseEntity.ok().build();
    }

}

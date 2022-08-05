package br.com.alelo.consumer.consumerpat.controller;

import br.com.alelo.consumer.consumerpat.entity.Country;
import br.com.alelo.consumer.consumerpat.exception.RecordNotFoundException;
import br.com.alelo.consumer.consumerpat.service.CountryService;
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
@RequestMapping("/country")
public class CountryController {

    private final CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService
                              ){
        this.countryService = countryService;
    }

    @GetMapping
    public ResponseEntity<Page<Country>> list(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                  @RequestParam(value = "size", defaultValue = "500") int size,
                                                                  @RequestParam(value = "direction", defaultValue = "asc") String direction,
                                                                  @RequestParam(value = "sortBy", defaultValue = "name") String sortBy)
            throws RecordNotFoundException {
        Pageable pageable = PageRequest.of(page, size, DirectionConverter.from(direction), sortBy);
        Page<Country> pageResult = countryService.findAll(pageable);
        return ResponseEntity.ok(pageResult);
    }

    @PostMapping
    public ResponseEntity<Country> create(@RequestBody Country country) throws RecordNotFoundException {
        return RestUtil.createWithLocation(getClass(), countryService.create(country));
    }

    @PutMapping
    public ResponseEntity<Country> update(@RequestBody Country country) throws RecordNotFoundException {
        return ResponseEntity.ok(countryService.update(country));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Country> read(@PathVariable("id") Integer id) throws RecordNotFoundException {
        return ResponseEntity.ok(countryService.read(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) throws RecordNotFoundException {
        countryService.delete(id);
        return ResponseEntity.ok().build();
    }

}

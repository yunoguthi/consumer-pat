package br.com.alelo.consumer.consumerpat.controller;

import br.com.alelo.consumer.consumerpat.entity.City;
import br.com.alelo.consumer.consumerpat.exception.RecordNotFoundException;
import br.com.alelo.consumer.consumerpat.service.CityService;
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
@RequestMapping("/city")
public class CityController {

    private final CityService cityService;

    @Autowired
    public CityController(CityService cityService
                              ){
        this.cityService = cityService;
    }

    @GetMapping
    public ResponseEntity<Page<City>> list(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                  @RequestParam(value = "size", defaultValue = "500") int size,
                                                                  @RequestParam(value = "direction", defaultValue = "asc") String direction,
                                                                  @RequestParam(value = "sortBy", defaultValue = "name") String sortBy)
            throws RecordNotFoundException {
        Pageable pageable = PageRequest.of(page, size, DirectionConverter.from(direction), sortBy);
        Page<City> pageResult = cityService.findAll(pageable);
        return ResponseEntity.ok(pageResult);
    }

    @PostMapping
    public ResponseEntity<City> create(@RequestBody City city) throws RecordNotFoundException {
        return RestUtil.createWithLocation(getClass(), cityService.create(city));
    }

    @PutMapping
    public ResponseEntity<City> update(@RequestBody City city) throws RecordNotFoundException {
        return ResponseEntity.ok(cityService.update(city));
    }

    @GetMapping("/{id}")
    public ResponseEntity<City> read(@PathVariable("id") Integer id) throws RecordNotFoundException {
        return ResponseEntity.ok(cityService.read(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) throws RecordNotFoundException {
        cityService.delete(id);
        return ResponseEntity.ok().build();
    }

}

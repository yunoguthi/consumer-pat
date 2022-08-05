package br.com.alelo.consumer.consumerpat.controller;

import br.com.alelo.consumer.consumerpat.entity.Address;
import br.com.alelo.consumer.consumerpat.exception.RecordNotFoundException;
import br.com.alelo.consumer.consumerpat.service.AddressService;
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
@RequestMapping("/address")
public class AddressController {

    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService
                              ){
        this.addressService = addressService;
    }

    @GetMapping
    public ResponseEntity<Page<Address>> list(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                  @RequestParam(value = "size", defaultValue = "500") int size,
                                                                  @RequestParam(value = "direction", defaultValue = "asc") String direction,
                                                                  @RequestParam(value = "sortBy", defaultValue = "name") String sortBy)
            throws RecordNotFoundException {
        Pageable pageable = PageRequest.of(page, size, DirectionConverter.from(direction), sortBy);
        Page<Address> pageResult = addressService.findAll(pageable);
        return ResponseEntity.ok(pageResult);
    }

    @PostMapping
    public ResponseEntity<Address> create(@RequestBody Address address) throws RecordNotFoundException {
        return RestUtil.createWithLocation(getClass(), addressService.create(address));
    }

    @PutMapping
    public ResponseEntity<Address> update(@RequestBody Address address) throws RecordNotFoundException {
        return ResponseEntity.ok(addressService.update(address));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Address> read(@PathVariable("id") Integer id) throws RecordNotFoundException {
        return ResponseEntity.ok(addressService.read(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) throws RecordNotFoundException {
        addressService.delete(id);
        return ResponseEntity.ok().build();
    }

}

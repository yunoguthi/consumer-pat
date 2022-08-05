package br.com.alelo.consumer.consumerpat.controller;

import br.com.alelo.consumer.consumerpat.entity.Item;
import br.com.alelo.consumer.consumerpat.exception.RecordNotFoundException;
import br.com.alelo.consumer.consumerpat.service.ItemService;
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
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService
                              ){
        this.itemService = itemService;
    }

    @GetMapping
    public ResponseEntity<Page<Item>> list(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                  @RequestParam(value = "size", defaultValue = "500") int size,
                                                                  @RequestParam(value = "direction", defaultValue = "asc") String direction,
                                                                  @RequestParam(value = "sortBy", defaultValue = "name") String sortBy)
            throws RecordNotFoundException {
        Pageable pageable = PageRequest.of(page, size, DirectionConverter.from(direction), sortBy);
        Page<Item> pageResult = itemService.findAll(pageable);
        return ResponseEntity.ok(pageResult);
    }

    @PostMapping
    public ResponseEntity<Item> create(@RequestBody Item item) throws RecordNotFoundException {
        return RestUtil.createWithLocation(getClass(), itemService.create(item));
    }

    @PutMapping
    public ResponseEntity<Item> update(@RequestBody Item item) throws RecordNotFoundException {
        return ResponseEntity.ok(itemService.update(item));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> read(@PathVariable("id") Integer id) throws RecordNotFoundException {
        return ResponseEntity.ok(itemService.read(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) throws RecordNotFoundException {
        itemService.delete(id);
        return ResponseEntity.ok().build();
    }

}

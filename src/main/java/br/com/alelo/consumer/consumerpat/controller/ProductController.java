package br.com.alelo.consumer.consumerpat.controller;

import br.com.alelo.consumer.consumerpat.entity.Product;
import br.com.alelo.consumer.consumerpat.exception.RecordNotFoundException;
import br.com.alelo.consumer.consumerpat.service.ProductService;
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
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService
                              ){
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Page<Product>> list(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                  @RequestParam(value = "size", defaultValue = "500") int size,
                                                                  @RequestParam(value = "direction", defaultValue = "asc") String direction,
                                                                  @RequestParam(value = "sortBy", defaultValue = "description") String sortBy)
            throws RecordNotFoundException {
        Pageable pageable = PageRequest.of(page, size, DirectionConverter.from(direction), sortBy);
        Page<Product> pageResult = productService.findAll(pageable);
        return ResponseEntity.ok(pageResult);
    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product product) throws RecordNotFoundException {
        return RestUtil.createWithLocation(getClass(), productService.create(product));
    }

    @PutMapping
    public ResponseEntity<Product> update(@RequestBody Product product) throws RecordNotFoundException {
        return ResponseEntity.ok(productService.update(product));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> read(@PathVariable("id") Integer id) throws RecordNotFoundException {
        return ResponseEntity.ok(productService.read(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) throws RecordNotFoundException {
        productService.delete(id);
        return ResponseEntity.ok().build();
    }

}

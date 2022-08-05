package br.com.alelo.consumer.consumerpat.controller;

import br.com.alelo.consumer.consumerpat.entity.State;
import br.com.alelo.consumer.consumerpat.exception.RecordNotFoundException;
import br.com.alelo.consumer.consumerpat.service.StateService;
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
@RequestMapping("/state")
public class StateController {

    private final StateService stateService;

    @Autowired
    public StateController(StateService stateService
                              ){
        this.stateService = stateService;
    }

    @GetMapping
    public ResponseEntity<Page<State>> list(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                  @RequestParam(value = "size", defaultValue = "500") int size,
                                                                  @RequestParam(value = "direction", defaultValue = "asc") String direction,
                                                                  @RequestParam(value = "sortBy", defaultValue = "name") String sortBy)
            throws RecordNotFoundException {
        Pageable pageable = PageRequest.of(page, size, DirectionConverter.from(direction), sortBy);
        Page<State> pageResult = stateService.findAll(pageable);
        return ResponseEntity.ok(pageResult);
    }

    @PostMapping
    public ResponseEntity<State> create(@RequestBody State state) throws RecordNotFoundException {
        return RestUtil.createWithLocation(getClass(), stateService.create(state));
    }

    @PutMapping
    public ResponseEntity<State> update(@RequestBody State state) throws RecordNotFoundException {
        return ResponseEntity.ok(stateService.update(state));
    }

    @GetMapping("/{id}")
    public ResponseEntity<State> read(@PathVariable("id") Integer id) throws RecordNotFoundException {
        return ResponseEntity.ok(stateService.read(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) throws RecordNotFoundException {
        stateService.delete(id);
        return ResponseEntity.ok().build();
    }

}

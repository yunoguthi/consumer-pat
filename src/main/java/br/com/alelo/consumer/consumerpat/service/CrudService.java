package br.com.alelo.consumer.consumerpat.service;

import br.com.alelo.consumer.consumerpat.exception.RecordNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CrudService<T, ID> {

    T create(T entity) throws RecordNotFoundException;

    void delete(ID id) throws RecordNotFoundException;

    T update(T entity) throws RecordNotFoundException;

    T read(ID id) throws RecordNotFoundException;

    Page<T> findAll(Pageable pageable);

    Iterable<T> getAll();

    void deleteAll();

    T save(T entity) throws RecordNotFoundException;

}

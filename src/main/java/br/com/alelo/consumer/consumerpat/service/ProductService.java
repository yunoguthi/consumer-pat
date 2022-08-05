package br.com.alelo.consumer.consumerpat.service;

import br.com.alelo.consumer.consumerpat.entity.Product;
import br.com.alelo.consumer.consumerpat.exception.RecordNotFoundException;
import br.com.alelo.consumer.consumerpat.respository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService implements CrudService<Product, Integer> {

    @Autowired
    private ProductRepository repository;

    @Override
    public Product create(Product entity) throws RecordNotFoundException {
        return save(entity);
    }

    @Override
    public void delete(Integer id) throws RecordNotFoundException {
        Product product = findByIdOrThrowException(id);
        repository.delete(product);
    }

    @Override
    public Product update(Product entity) throws RecordNotFoundException {
        findByIdOrThrowException(entity.getId());
        return save(entity);
    }

    @Override
    public Product read(Integer id) throws RecordNotFoundException {
        return findByIdOrThrowException(id);
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Iterable<Product> getAll() {
        return repository.findAll();
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public Product save(Product entity) throws RecordNotFoundException {
        return repository.save(entity);
    }

    public Product findByDescription(String description) throws RecordNotFoundException {
        return findByDescriptonOrThrowException(description);
    }

    public Product findById(Integer id) throws RecordNotFoundException {
        return findByIdOrThrowException(id);
    }

    private Product findByIdOrThrowException(Integer id) throws RecordNotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Registro nao encontrado com o id " + id));
    }

    private Product findByDescriptonOrThrowException(String description) throws RecordNotFoundException {
        return repository.findByDescription(description)
                .orElseThrow(() -> new RecordNotFoundException("Registro nao encontrado com a descrição " + description));
    }

}

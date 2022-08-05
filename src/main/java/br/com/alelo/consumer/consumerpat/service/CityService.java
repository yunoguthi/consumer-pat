package br.com.alelo.consumer.consumerpat.service;

import br.com.alelo.consumer.consumerpat.entity.City;
import br.com.alelo.consumer.consumerpat.exception.RecordNotFoundException;
import br.com.alelo.consumer.consumerpat.respository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CityService implements CrudService<City, Integer> {

    @Autowired
    private CityRepository repository;

    @Override
    public City create(City entity) throws RecordNotFoundException {
        return save(entity);
    }

    @Override
    public void delete(Integer id) throws RecordNotFoundException {
        City product = findByIdOrThrowException(id);
        repository.delete(product);
    }

    @Override
    public City update(City entity) throws RecordNotFoundException {
        findByIdOrThrowException(entity.getId());
        return save(entity);
    }

    @Override
    public City read(Integer id) throws RecordNotFoundException {
        return findByIdOrThrowException(id);
    }

    @Override
    public Page<City> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Iterable<City> getAll() {
        return repository.findAll();
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public City save(City entity) throws RecordNotFoundException {
        return repository.save(entity);
    }

    private City findByIdOrThrowException(Integer id) throws RecordNotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Registro nao encontrado com o id " + id));
    }

}

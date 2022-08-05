package br.com.alelo.consumer.consumerpat.service;

import br.com.alelo.consumer.consumerpat.entity.Establishment;
import br.com.alelo.consumer.consumerpat.exception.RecordNotFoundException;
import br.com.alelo.consumer.consumerpat.respository.EstablishmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EstablishmentService implements CrudService<Establishment, Integer>{

    @Autowired
    private EstablishmentRepository repository;

    @Override
    public Establishment create(Establishment entity) throws RecordNotFoundException {
        return save(entity);
    }

    @Override
    public void delete(Integer id) throws RecordNotFoundException {
        Establishment Establishment = findByIdOrThrowException(id);
        repository.delete(Establishment);
    }

    @Override
    public Establishment update(Establishment entity) throws RecordNotFoundException {
        findByIdOrThrowException(entity.getId());
        return save(entity);    }

    @Override
    public Establishment read(Integer id) throws RecordNotFoundException {
        return findByIdOrThrowException(id);
    }

    @Override
    public Page<Establishment> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Iterable<Establishment> getAll() {
        return repository.findAll();
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public Establishment save(Establishment entity) throws RecordNotFoundException {
        return repository.save(entity);
    }

    public Establishment findByName(String name) throws RecordNotFoundException {
        Establishment establishment = findByNameOrThrowException(name);
        return establishment;
    }

    public Establishment findById(Integer id) throws RecordNotFoundException {
        Establishment establishment = findByIdOrThrowException(id);
        return establishment;
    }

    private Establishment findByIdOrThrowException(Integer id) throws RecordNotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Registro nao encontrado com o id " + id));
    }

    private Establishment findByNameOrThrowException(String name) throws RecordNotFoundException {
        return repository.findByName(name)
                .orElseThrow(() -> new RecordNotFoundException("Registro nao encontrado com o name " + name));
    }

}

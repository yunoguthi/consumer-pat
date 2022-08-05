package br.com.alelo.consumer.consumerpat.service;

import br.com.alelo.consumer.consumerpat.entity.Consumer;
import br.com.alelo.consumer.consumerpat.exception.RecordNotFoundException;
import br.com.alelo.consumer.consumerpat.entity.enums.ECardType;
import br.com.alelo.consumer.consumerpat.respository.ConsumerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService implements CrudService<Consumer, Integer>{

    @Autowired
    private ConsumerRepository repository;

    @Override
    public Consumer create(Consumer entity) throws RecordNotFoundException {
        return save(entity);
    }

    @Override
    public void delete(Integer id) throws RecordNotFoundException {
        Consumer Consumer = findByIdOrThrowException(id);
        repository.delete(Consumer);
    }

    @Override
    public Consumer update(Consumer entity) throws RecordNotFoundException {
        findByIdOrThrowException(entity.getId());
        return save(entity);    }

    @Override
    public Consumer read(Integer id) throws RecordNotFoundException {
        return findByIdOrThrowException(id);
    }

    @Override
    public Page<Consumer> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Iterable<Consumer> getAll() {
        return repository.findAll();
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public Consumer save(Consumer entity) throws RecordNotFoundException {
        return repository.save(entity);
    }

    private Consumer findByIdOrThrowException(Integer id) throws RecordNotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Registro nao encontrado com o id " + id));
    }

}

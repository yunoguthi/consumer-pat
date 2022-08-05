package br.com.alelo.consumer.consumerpat.service;

import br.com.alelo.consumer.consumerpat.entity.Address;
import br.com.alelo.consumer.consumerpat.exception.RecordNotFoundException;
import br.com.alelo.consumer.consumerpat.respository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AddressService implements CrudService<Address, Integer>{

    @Autowired
    private AddressRepository repository;

    @Override
    public Address create(Address entity) throws RecordNotFoundException {
        return save(entity);
    }

    @Override
    public void delete(Integer id) throws RecordNotFoundException {
        Address address = findByIdOrThrowException(id);
        repository.delete(address);
    }

    @Override
    public Address update(Address entity) throws RecordNotFoundException {
        findByIdOrThrowException(entity.getId());
        return save(entity);    }

    @Override
    public Address read(Integer id) throws RecordNotFoundException {
        return findByIdOrThrowException(id);
    }

    @Override
    public Page<Address> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Iterable<Address> getAll() {
        return repository.findAll();
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public Address save(Address entity) throws RecordNotFoundException {
        return repository.save(entity);
    }

    private Address findByIdOrThrowException(Integer id) throws RecordNotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Registro nao encontrado com o id " + id));
    }

}

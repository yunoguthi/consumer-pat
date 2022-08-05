package br.com.alelo.consumer.consumerpat.entity;

import br.com.alelo.consumer.consumerpat.respository.AddressRepository;
import br.com.alelo.consumer.consumerpat.util.RandomGenerator;
import br.com.alelo.consumer.consumerpat.util.RepositoryTestUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.lang.reflect.InvocationTargetException;

import static br.com.alelo.consumer.consumerpat.util.RepositoryTestUtil.assertUpdate;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
public class AddressRepositoryTest {

    @Autowired
    private AddressRepository repository;

    public static Address createNewEntity() {
        Address address = new Address();
        address.setNumber(RandomGenerator.randomize(10));
        address.setCity(CityRepositoryTest.createNewEntity("save address"));
        address.setPortalCode(RandomGenerator.randomize(10));
        address.setStreet(RandomGenerator.randomize(10));
        return address;
    }

    @Test
    public void save() throws InvocationTargetException, IllegalAccessException {
        RepositoryTestUtil.assertSave(repository, createNewEntity());
    }

    @Test
    public void update() throws InvocationTargetException, IllegalAccessException {
        assertUpdate(repository,
                createNewEntity(),
                address-> {
                    address.setNumber("a");
                    address.setCity(CityRepositoryTest.createNewEntity("save address"));
                    address.setPortalCode("a");
                    address.setStreet("a");
                });
    }

    @Test
    public void delete() throws InvocationTargetException, IllegalAccessException {
        RepositoryTestUtil.assertDelete(repository, createNewEntity());
    }

    @Test
    public void findAll() {
        RepositoryTestUtil.assertFindAll(repository, createNewEntity());
    }

}

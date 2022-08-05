package br.com.alelo.consumer.consumerpat.entity;

import br.com.alelo.consumer.consumerpat.entity.enums.ECardType;
import br.com.alelo.consumer.consumerpat.respository.EstablishmentRepository;
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
public class EstablishmentRepositoryTest {

    @Autowired
    private EstablishmentRepository repository;

    public static Establishment createNewEntity(String name) {
        Establishment establishment = new Establishment();
        establishment.setName(name);
        establishment.setEstablishmentType(ECardType.FOOD);
        return establishment;
    }

    @Test
    public void save() throws InvocationTargetException, IllegalAccessException {
        RepositoryTestUtil.assertSave(repository, createNewEntity(RandomGenerator.randomize(10)));
    }

    @Test
    public void update() throws InvocationTargetException, IllegalAccessException {
        assertUpdate(repository,
                createNewEntity(RandomGenerator.randomize(10)),
                establishment-> {
                    establishment.setName(RandomGenerator.randomize(10));
                    establishment.setEstablishmentType(ECardType.FUEL);
                });
    }

    @Test
    public void delete() throws InvocationTargetException, IllegalAccessException {
        RepositoryTestUtil.assertDelete(repository, createNewEntity("delete"));
    }

    @Test
    public void findAll() {
        RepositoryTestUtil.assertFindAll(repository, createNewEntity(RandomGenerator.randomize(10)));
    }

}

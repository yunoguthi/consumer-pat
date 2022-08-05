package br.com.alelo.consumer.consumerpat.entity;

import br.com.alelo.consumer.consumerpat.respository.ConsumerRepository;
import br.com.alelo.consumer.consumerpat.util.RandomGenerator;
import br.com.alelo.consumer.consumerpat.util.RepositoryTestUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import static br.com.alelo.consumer.consumerpat.util.RepositoryTestUtil.assertUpdate;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
public class ConsumerRepositoryTest {

    @Autowired
    private ConsumerRepository repository;

    public static Consumer createNewEntity() {
        Consumer consumer = new Consumer();
        consumer.setAddress(AddressRepositoryTest.createNewEntity());
        consumer.setName(RandomGenerator.randomize(10));
        consumer.setEmail(RandomGenerator.randomize(10));
        consumer.setBirthDate(new Date());
        consumer.setDocumentNumber(RandomGenerator.randomize(10));
        consumer.setMobilePhoneNumber(RandomGenerator.randomize(10));
        consumer.setPhoneNumber(RandomGenerator.randomize(10));
        consumer.setResidencePhoneNumber(RandomGenerator.randomize(10));
        return consumer;
    }

    @Test
    public void save() throws InvocationTargetException, IllegalAccessException {
        RepositoryTestUtil.assertSave(repository, createNewEntity());
    }


    @Test
    public void update() throws InvocationTargetException, IllegalAccessException {
        assertUpdate(repository,
                createNewEntity(),
                consumer-> {
                    consumer.setAddress(AddressRepositoryTest.createNewEntity());
                    consumer.setName(RandomGenerator.randomize(10));
                    consumer.setEmail(RandomGenerator.randomize(10));
                    consumer.setBirthDate(new Date());
                    consumer.setDocumentNumber(RandomGenerator.randomize(10));
                    consumer.setMobilePhoneNumber(RandomGenerator.randomize(10));
                    consumer.setPhoneNumber(RandomGenerator.randomize(10));
                    consumer.setResidencePhoneNumber(RandomGenerator.randomize(10));
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

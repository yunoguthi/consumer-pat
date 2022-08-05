package br.com.alelo.consumer.consumerpat.entity;

import br.com.alelo.consumer.consumerpat.respository.CountryRepository;
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
public class CountryRepositoryTest {

    @Autowired
    private CountryRepository repository;

    public static Country createNewEntity(String pais) {
        Country country = new Country();
        country.setName(pais);
        return country;
    }

    @Test
    public void save() throws InvocationTargetException, IllegalAccessException {
        RepositoryTestUtil.assertSave(repository, createNewEntity("save"));
    }

    @Test
    public void update() throws InvocationTargetException, IllegalAccessException {
        assertUpdate(repository,
                createNewEntity("original"),
                country-> {
                    country.setName("update");
                });
    }

    @Test
    public void delete() throws InvocationTargetException, IllegalAccessException {
        RepositoryTestUtil.assertDelete(repository, createNewEntity("delete"));
    }

    @Test
    public void findAll() {
        RepositoryTestUtil.assertFindAll(repository, createNewEntity("findAll"));
    }

}

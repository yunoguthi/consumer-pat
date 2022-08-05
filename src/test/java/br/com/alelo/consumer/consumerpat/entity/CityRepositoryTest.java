package br.com.alelo.consumer.consumerpat.entity;

import br.com.alelo.consumer.consumerpat.respository.CityRepository;
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
public class CityRepositoryTest {

    @Autowired
    private CityRepository repository;

    public static City createNewEntity(String name) {
        City city = new City();
        city.setName(name);
        city.setState(StateRepositoryTest.createNewEntity("save city"));
        return city;
    }

    @Test
    public void save() throws InvocationTargetException, IllegalAccessException {
        RepositoryTestUtil.assertSave(repository, createNewEntity("save"));
    }

    @Test
    public void update() throws InvocationTargetException, IllegalAccessException {
        assertUpdate(repository,
                createNewEntity("original"),
                city-> {
                    city.setName("update");
                    city.setState(StateRepositoryTest.createNewEntity("update city"));
                });
    }

    @Test
    public void delete() throws InvocationTargetException, IllegalAccessException {
        RepositoryTestUtil.assertDelete(repository, createNewEntity("delete"));
    }

    @Test
    public void findAll() {
        RepositoryTestUtil.assertFindAll(repository, createNewEntity("findall city"));
    }

}

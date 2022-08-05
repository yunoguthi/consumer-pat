package br.com.alelo.consumer.consumerpat.entity;

import br.com.alelo.consumer.consumerpat.respository.StateRepository;
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
public class StateRepositoryTest {

    @Autowired
    private StateRepository repository;

    public static State createNewEntity(String name) {
        State state = new State();
        state.setName(name);
        state.setCountry(CountryRepositoryTest.createNewEntity("save state"));
        return state;
    }

    @Test
    public void save() throws InvocationTargetException, IllegalAccessException {
        RepositoryTestUtil.assertSave(repository, createNewEntity("save"));
    }

    @Test
    public void update() throws InvocationTargetException, IllegalAccessException {
        assertUpdate(repository,
                createNewEntity("original"),
                state-> {
                    state.setName("update");
                    state.setCountry(CountryRepositoryTest.createNewEntity("update state"));
                });
    }

    @Test
    public void delete() throws InvocationTargetException, IllegalAccessException {
        RepositoryTestUtil.assertDelete(repository, createNewEntity("delete"));
    }

    @Test
    public void findAll() {
        RepositoryTestUtil.assertFindAll(repository, createNewEntity("findAll state"));
    }

}

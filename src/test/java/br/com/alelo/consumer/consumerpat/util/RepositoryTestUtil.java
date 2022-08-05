package br.com.alelo.consumer.consumerpat.util;

import org.assertj.core.api.Assertions;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.persistence.Id;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

public final class RepositoryTestUtil {

    public static <T, K> void assertSave(CrudRepository<T, K> repository, T entity)
            throws InvocationTargetException, IllegalAccessException {
        T entitySaved = repository.save(entity);
        K k = getId(entitySaved);
        assertThat(k, is(notNullValue()));
        assertThat(repository.findById(k).isPresent(), is(true));
    }

    public static <T, K> void assertUpdate(CrudRepository<T, K> repository, T entity, Consumer<T> updater)
            throws InvocationTargetException, IllegalAccessException {
        T entitySaved = repository.save(entity);

        updater.accept(entitySaved);
        entitySaved = repository.save(entitySaved);

        // Find it again and check if the field changed was correctly updated
        T entityLoaded = repository.findById(getId(entitySaved)).orElse(null);

        if (Objects.nonNull(entityLoaded)) {
            assertThat(entityLoaded, is(entitySaved));
        } else {
            Assertions.fail("Update failed");
        }
    }

    public static <T,K> void assertDelete(CrudRepository<T, K> repository, T entity)
            throws InvocationTargetException, IllegalAccessException {
        T entitySaved = repository.save(entity);
        repository.delete(entitySaved);

        assertThat(repository.findById(getId(entitySaved)).isPresent(), is(false));
    }

    public static <T,K> void assertFindAll(CrudRepository<T, K> repository, T entity) {
        repository.save(entity);
        assertThat(repository.findAll().iterator().hasNext(), is(true));

    }

    public static <T,K> void assertFindAll(PagingAndSortingRepository<T, K> repository, T entity) {
        repository.save(entity);
        assertThat(repository.findAll(Pageable.unpaged()).iterator().hasNext(), is(true));
    }

    private static <T, K> K getId(T entity) throws InvocationTargetException, IllegalAccessException {
        Field idField = Arrays.stream(entity.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No @Id field found"));

        String getterName = "get"
                + idField.getName().substring(0, 1).toUpperCase()
                + idField.getName().substring(1);

        Method getterMethod = Arrays.stream(entity.getClass().getMethods())
                .filter(method -> method.getName().equals(getterName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No getter method related to @Id field"));

        K k = (K) getterMethod.invoke(entity);

        return k;
    }
}

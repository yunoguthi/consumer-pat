package br.com.alelo.consumer.consumerpat.entity;

import br.com.alelo.consumer.consumerpat.entity.enums.ECardType;
import br.com.alelo.consumer.consumerpat.respository.CardRepository;
import br.com.alelo.consumer.consumerpat.util.RandomGenerator;
import br.com.alelo.consumer.consumerpat.util.RepositoryTestUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

import static br.com.alelo.consumer.consumerpat.util.RepositoryTestUtil.assertUpdate;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
public class CardRepositoryTest {

    @Autowired
    private CardRepository repository;

    public static Card createNewEntity(String cardNumber) {
        Card card = new Card();
        card.setCardType(ECardType.FOOD);
        card.setCardBalance(new BigDecimal(4552345.54));
        card.setOwner(ConsumerRepositoryTest.createNewEntity());
        card.setCardNumber(cardNumber);
        return card;
    }

    @Test
    public void save() throws InvocationTargetException, IllegalAccessException {
        RepositoryTestUtil.assertSave(repository, createNewEntity(RandomGenerator.randomize(10)));
    }

    @Test
    public void update() throws InvocationTargetException, IllegalAccessException {
        assertUpdate(repository,
                createNewEntity(RandomGenerator.randomize(10)),
                card-> {
                    card.setCardType(ECardType.DRUG_STORE);
                    card.setCardBalance(new BigDecimal(88888.99));
                    card.setOwner(ConsumerRepositoryTest.createNewEntity());
                    card.setCardNumber(RandomGenerator.randomize(10));
                });
    }

    @Test
    public void delete() throws InvocationTargetException, IllegalAccessException {
        RepositoryTestUtil.assertDelete(repository, createNewEntity(RandomGenerator.randomize(10)));
    }

    @Test
    public void findAll() {
        RepositoryTestUtil.assertFindAll(repository, createNewEntity(RandomGenerator.randomize(10)));
    }

}

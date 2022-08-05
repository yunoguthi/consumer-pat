package br.com.alelo.consumer.consumerpat.service;

import br.com.alelo.consumer.consumerpat.entity.CardRepositoryTest;
import br.com.alelo.consumer.consumerpat.exception.RecordNotFoundException;
import br.com.alelo.consumer.consumerpat.entity.Card;
import br.com.alelo.consumer.consumerpat.util.RandomGenerator;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
@Slf4j
@SpringBootTest
@ActiveProfiles("test")
public class CardServiceTest {

    @Autowired
    private CardService service;

    @Test
    public void adicionarSaldo() throws InvocationTargetException, IllegalAccessException, RecordNotFoundException {
        String cardNumber = RandomGenerator.randomize(10);
        Card card = service.save(CardRepositoryTest.createNewEntity(cardNumber));
        if (Objects.nonNull(card)) {
            assertThat(card, is(service.adicionarSaldo(cardNumber, 9.9)));
        } else {
            Assertions.fail("cardService.adicionarSaldo failed");
        }
    }

}

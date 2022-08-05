package br.com.alelo.consumer.consumerpat.service;

import br.com.alelo.consumer.consumerpat.entity.*;
import br.com.alelo.consumer.consumerpat.entity.enums.ECardType;
import br.com.alelo.consumer.consumerpat.exception.PurchaseNotAllowedException;
import br.com.alelo.consumer.consumerpat.exception.RecordNotFoundException;
import br.com.alelo.consumer.consumerpat.respository.ProductRepository;
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
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
@Slf4j
@SpringBootTest
@ActiveProfiles("test")
public class ExtractServiceTest {

    @Autowired
    private ExtractService service;

    @Autowired
    private EstablishmentService establishmentService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CardService cardService;

    @Test
    public void buy() throws InvocationTargetException, IllegalAccessException, RecordNotFoundException, PurchaseNotAllowedException {
//        String extractNumber = "teste";
//        Extract extract = service.findBy(extractNumber);
//        if (Objects.nonNull(extract)) {
//            assertThat(extract, is(service.buy(extractNumber, 9.9)));
//        } else {
//            Assertions.fail("extractService.adicionarSaldo failed");
//        }

        String establishmentName = RandomGenerator.randomize(10);
        establishmentService.save(EstablishmentRepositoryTest.createNewEntity(establishmentName));
        String cardNumber = RandomGenerator.randomize(10);
        cardService.save(CardRepositoryTest.createNewEntity(cardNumber));
        String productDescription = RandomGenerator.randomize(10);
        productService.save(ProductRepositoryTest.createNewEntity(productDescription));
        double value = 10.0;

        Extract entitySaved = service.buy(ECardType.FOOD, establishmentName, cardNumber, productDescription, value);
        assertThat(entitySaved, is(notNullValue()));
    }

}

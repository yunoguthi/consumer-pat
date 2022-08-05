package br.com.alelo.consumer.consumerpat.entity;


import br.com.alelo.consumer.consumerpat.entity.enums.ECardType;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;


@Data
@Entity
@Table(name = "card", indexes = @Index(
        name="idx_card", unique=true, columnList = "cardNumber"
))
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Card {

    public static final int CARD_NUMBER_LENGTH = 20;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    //cards
    @Column(name = "cardNumber", nullable = false, length = CARD_NUMBER_LENGTH)
    private String cardNumber;

    @Column(name = "cardBalance", nullable = false)
    private BigDecimal cardBalance;

    @Column(name = "cardType", nullable = false)
    private ECardType cardType;

    @ManyToOne(cascade= CascadeType.ALL)
    private Consumer owner;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card consumer = (Card) o;
        return cardNumber.equals(consumer.cardNumber); // partindo do princípio que o número é único
    }

}

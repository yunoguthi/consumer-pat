package br.com.alelo.consumer.consumerpat.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


@Data
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Extract {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "dateBuy", nullable = false)
    private Date dateBuy;

    @ManyToOne(cascade= CascadeType.MERGE)
    private Card cardNumber;

    @Column(name = "value", nullable = false)
    private BigDecimal value;

    @ManyToOne(cascade= CascadeType.MERGE)
    private Establishment establishment;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Extract extract = (Extract) o;
        return  extract.dateBuy.equals(this.dateBuy) &&
                extract.cardNumber.equals(this.cardNumber) &&
                extract.establishment.equals(this.establishment);
    }

}

package br.com.alelo.consumer.consumerpat.entity;

import lombok.*;

import javax.persistence.*;


@Data
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    public static final int DESCRIPTION_LENGTH = 50;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "description", nullable = false, length = DESCRIPTION_LENGTH)
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return  product.description.equals(this.description);
    }

}

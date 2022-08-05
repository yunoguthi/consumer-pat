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
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(cascade= CascadeType.MERGE)
    private Product product;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @ManyToOne(cascade= CascadeType.MERGE)
    private Extract extract;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return  item.extract.equals(this.extract) &&
                item.product.equals(this.product);
    }

}

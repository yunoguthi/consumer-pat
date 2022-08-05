package br.com.alelo.consumer.consumerpat.entity;

import br.com.alelo.consumer.consumerpat.entity.enums.ECardType;
import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "establishment", indexes = @Index(
        name="idx_establishment", unique=true, columnList = "name"
))
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Establishment {

    public static final int NAME_LENGTH = 50;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name", nullable = false, length = NAME_LENGTH)
    private String name;

    @Column(name = "establishmentType", nullable = false)
    private ECardType establishmentType;

}

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
public class Address {

    public static final int STREET_LENGTH = 50;
    public static final int NUMBER_LENGTH = 10;
    public static final int PORTAL_CODE_LENGTH = 50;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "street", nullable = false, length = STREET_LENGTH)
    private String street;

    @Column(name = "number", nullable = false, length = NUMBER_LENGTH)
    private String number;

    @ManyToOne(cascade= CascadeType.ALL)
    private City city;

    @Column(name = "portalCode", nullable = false, length = PORTAL_CODE_LENGTH)
    private String portalCode;

}

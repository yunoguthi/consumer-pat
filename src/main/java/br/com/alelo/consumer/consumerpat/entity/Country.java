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
public class Country {

    public static final int NAME_LENGTH = 50;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name", nullable = false, length = NAME_LENGTH)
    private String name;

}

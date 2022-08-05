package br.com.alelo.consumer.consumerpat.entity;


import lombok.*;

import javax.persistence.*;
import java.util.Date;


@Data
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Consumer {

    public static final int NAME_LENGTH = 50;
    public static final int DOCUMENT_NUMBER_LENGTH = 20;
    public static final int MOBILE_PHONE_NUMBER_LENGTH = 20;
    public static final int RESIDENCE_PHONE_LENGTH = 20;
    public static final int PHONE_NUMBER_LENGTH = 20;
    public static final int EMAIL_LENGTH = 20;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name", nullable = false, length = NAME_LENGTH)
    private String name;
    @Column(name = "documentNumber", nullable = false, length = DOCUMENT_NUMBER_LENGTH)
    private String documentNumber;
    @Column(name = "birthDate", nullable = true)
    private Date birthDate;

    //contacts
    @Column(name = "mobilePhoneNumber", nullable = false, length = MOBILE_PHONE_NUMBER_LENGTH)
    private String mobilePhoneNumber;

    @Column(name = "residencePhoneNumber", nullable = true, length = RESIDENCE_PHONE_LENGTH)
    private String residencePhoneNumber;
    @Column(name = "phoneNumber", nullable = true, length = PHONE_NUMBER_LENGTH)
    private String phoneNumber;
    @Column(name = "email", nullable = false, length = EMAIL_LENGTH)
    private String email;

    @ManyToOne(cascade= CascadeType.ALL)
    private Address address;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Consumer consumer = (Consumer) o;
        return documentNumber.equals(consumer.documentNumber)
                && email.equals(consumer.email)
                && mobilePhoneNumber.equals(consumer.mobilePhoneNumber);
    }


}

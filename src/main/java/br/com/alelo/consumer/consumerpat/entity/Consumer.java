package br.com.alelo.consumer.consumerpat.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;


@Data
@Entity
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Consumer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DOCUMENT_NUMBER")
    private String documentNumber;

    @Column(name = "BIRTH_DATE")
    private LocalDate birthDate;

    @Embedded
    private Contact contact;

    @Embedded
    private Address address;

}

package br.com.platformbuilders.consumer.entity;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDate;

import static javax.persistence.GenerationType.AUTO;

@Data
@Builder
@Entity
@Table(name = "CONSUMER")
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Consumer implements Serializable {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Integer id;

    @Column(name = "NAME")
    @NotBlank(message = "Consumer name is required")
    private String name;

    @Column(name = "DOCUMENT_NUMBER")
    @NotBlank(message = "Consumer document number is required")
    private String documentNumber;

    @Column(name = "BIRTH_DATE")
    private LocalDate birthDate;

    @Embedded
    private Contact contact;

    @Embedded
    private Address address;

}


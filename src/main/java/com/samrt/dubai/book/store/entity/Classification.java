package com.samrt.dubai.book.store.entity;


import lombok.*;

import javax.persistence.*;


@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table
public class Classification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String classificationName;

    public Classification(String classificationName) {
        this.classificationName = classificationName;
    }

}

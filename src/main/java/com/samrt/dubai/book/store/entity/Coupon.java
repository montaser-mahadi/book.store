package com.samrt.dubai.book.store.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private int discount;
    private String couponCode;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "classification_id", referencedColumnName = "id")
    private Classification classification;
    @Transient
    private boolean checkAvailable;

    public Coupon(int discount, String couponCode, Classification classification, boolean checkAvailable) {
        this.discount = discount;
        this.couponCode = couponCode;
        this.classification = classification;
        this.checkAvailable = checkAvailable;
    }
}

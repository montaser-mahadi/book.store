package com.samrt.dubai.book.store.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(unique = true)
    private String name;
    private String description;

    //@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    //private Coupon discount;
    private String author;

    // the book type or book classification
    @JsonIgnore
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "classification_id", referencedColumnName = "id")
    private Classification classification;
    private double price;

    private int availableQuantity;
    @Column(unique = true)
    private String isbn;

    public Book(String name, String description, String author,
                Classification classification, double price, int availableQuantity, String isbn) {
        this.name = name;
        this.description = description;
        this.author = author;
        this.classification = classification;
        this.price = price;
        this.availableQuantity = availableQuantity;
        this.isbn = isbn;
    }
}

package com.samrt.dubai.book.store.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@ToString
@Entity
@Getter
@Setter
@Table(name = "myorder")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String orderDescription;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    private String couponCode;


    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = ShoppingCart.class)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private List<ShoppingCart> cartItems;

    private double totalPrice =0;

    public Order() {
    }

    public Order(String orderDescription, Customer customer, List<ShoppingCart> cartItems,
                 double totalPrice,String couponCode) {
        this.orderDescription = orderDescription;
        this.customer = customer;
        this.cartItems = cartItems;
        this.totalPrice = totalPrice;
        this.couponCode = couponCode;
    }
}

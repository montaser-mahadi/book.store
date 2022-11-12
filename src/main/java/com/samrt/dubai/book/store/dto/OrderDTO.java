package com.samrt.dubai.book.store.dto;

import com.samrt.dubai.book.store.entity.ShoppingCart;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class OrderDTO {

    private String orderDescription;
    private List<ShoppingCart> cartItems;
    private String customerEmail;
    private String customerName;

    private String coupon;

    public OrderDTO() {
    }

    public OrderDTO(String orderDescription, List<ShoppingCart> cartItems, String customerEmail,
                    String customerName, String coupon) {
        this.orderDescription = orderDescription;
        this.cartItems = cartItems;
        this.customerEmail = customerEmail;
        this.customerName = customerName;
        this.coupon = coupon;
    }
}

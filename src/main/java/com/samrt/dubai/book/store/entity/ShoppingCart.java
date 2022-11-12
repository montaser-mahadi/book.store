package com.samrt.dubai.book.store.entity;


import lombok.*;

import javax.persistence.*;

/*@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
*/
@ToString
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Integer bookId;

    private String bookName;
    private int quantity;
    private double amount;

    public ShoppingCart() {
    }

    public ShoppingCart(int bookId, String bookName, int quantity, float amount) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.quantity = quantity;
        this.amount = amount;
    }
    public ShoppingCart(int bookId, String bookName, int quantity, float amount, String coupon) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.quantity = quantity;
        this.amount = amount;
    }

    public ShoppingCart(int bookId, int quantity) {
        this.bookId = bookId;
        this.quantity = quantity;
    }
}

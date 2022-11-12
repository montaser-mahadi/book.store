package com.samrt.dubai.book.store.dto;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ResponseOrderDTO {

    private double amount;
    private int invoiceNumber;
    private String date;
    private String OrderDescription;
    private int orderId;
    private String coupon;


}

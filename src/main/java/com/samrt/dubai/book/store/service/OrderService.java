package com.samrt.dubai.book.store.service;

import com.samrt.dubai.book.store.entity.*;
import com.samrt.dubai.book.store.repository.BookRepository;
import com.samrt.dubai.book.store.repository.CouponRepository;
import com.samrt.dubai.book.store.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    final private OrderRepository orderRepository;
    @Autowired
    final private BookRepository bookRepository;
    @Autowired
    final private CouponRepository couponRepository;


    public OrderService(OrderRepository orderRepository,
                        BookRepository bookRepository, CouponRepository couponRepository) {
        this.orderRepository = orderRepository;
        this.bookRepository = bookRepository;
        this.couponRepository  = couponRepository;
    }

    public Order getOrderDetail(int orderId) {
        Optional<Order> order = this.orderRepository.findById(orderId);
        return order.isPresent() ? order.get() : null;
    }

    public double getCartAmount(List<ShoppingCart> shoppingCartList, String couponCode) {

        double totalCartAmount = 0;
        double singleCartAmount;
        int availableQuantity = 0;
        double percent;
        double percentMinus;
        //List<Integer> books =  shoppingCartList.stream().map((value)->value.getBookId()).collect(Collectors.toList());
        for (ShoppingCart cart : shoppingCartList) {

            Integer BookId = cart.getBookId();
            Optional<Book> bookOptional = bookRepository.findById(BookId);
            if (bookOptional.isPresent()) {
                Book book = bookOptional.get();
                if (book.getAvailableQuantity() < cart.getQuantity()) {
                    singleCartAmount = book.getPrice() * book.getAvailableQuantity();
                    cart.setQuantity(book.getAvailableQuantity());
                } else {
                    singleCartAmount = cart.getQuantity() * book.getPrice();
                    availableQuantity = book.getAvailableQuantity() - cart.getQuantity();
                }
                Classification classification =  book.getClassification();
                 Optional<Coupon> optionalCoupon  = couponRepository.findByCouponCode(couponCode);
                 if(optionalCoupon.isPresent())
                 {

                     Coupon couponObject = optionalCoupon.get();
                     if(Objects.equals(couponObject.getClassification().getId(), classification.getId())) {

                         percent = (couponObject.getDiscount()*book.getPrice()*cart.getQuantity())/100;

                         percentMinus = singleCartAmount;

                         totalCartAmount = totalCartAmount+percentMinus-percent;

                         book.setAvailableQuantity(availableQuantity);
                         availableQuantity = 0;
                         cart.setBookName(book.getName());
                         cart.setAmount(singleCartAmount);
                         bookRepository.save(book);
                     }
                     else {
                         totalCartAmount = totalCartAmount + singleCartAmount;
                         book.setAvailableQuantity(availableQuantity);
                         availableQuantity=0;
                         cart.setBookName(book.getName());
                         cart.setAmount(singleCartAmount);
                         bookRepository.save(book);
                     }
                 }
                 else {
                     totalCartAmount = totalCartAmount + singleCartAmount;
                     book.setAvailableQuantity(availableQuantity);
                     availableQuantity=0;
                     cart.setBookName(book.getName());
                     cart.setAmount(singleCartAmount);
                     bookRepository.save(book);
                 }

            }
        }
        return totalCartAmount;
    }

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }
}

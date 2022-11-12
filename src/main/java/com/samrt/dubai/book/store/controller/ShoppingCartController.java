package com.samrt.dubai.book.store.controller;

import com.samrt.dubai.book.store.dto.OrderDTO;
import com.samrt.dubai.book.store.dto.ResponseOrderDTO;
import com.samrt.dubai.book.store.entity.Customer;
import com.samrt.dubai.book.store.entity.Order;
import com.samrt.dubai.book.store.repository.BookRepository;
import com.samrt.dubai.book.store.repository.CouponRepository;
import com.samrt.dubai.book.store.service.CustomerService;
import com.samrt.dubai.book.store.service.OrderService;
import com.samrt.dubai.book.store.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping("/api")
public class ShoppingCartController {

    private OrderService orderService;
    private BookRepository bookRepository;
    private CustomerService customerService;

    @Autowired
    private CouponRepository couponRepository;


    public ShoppingCartController(OrderService orderService, BookRepository bookRepository, CustomerService customerService) {
        this.orderService = orderService;
        this.bookRepository = bookRepository;
        this.customerService = customerService;
    }

    private Logger logger = LoggerFactory.getLogger(ShoppingCartController.class);

    @GetMapping(value = "/getOrder/{orderId}")
    public ResponseEntity<Order> getOrderDetails(@PathVariable int orderId) {

        Order order = orderService.getOrderDetail(orderId);
        return ResponseEntity.ok(order);
    }


    @PostMapping("/createOrder")
    public ResponseEntity<ResponseOrderDTO> createOrder(@RequestBody OrderDTO orderDTO) {
        logger.info("Request Payload " + orderDTO.toString());
        ResponseOrderDTO responseOrderDTO = new ResponseOrderDTO();
        double amount = orderService.getCartAmount(orderDTO.getCartItems(), orderDTO.getCoupon());

        Customer customer = new Customer(orderDTO.getCustomerName(), orderDTO.getCustomerEmail());
        Integer customerIdFromDb = customerService.isCustomerPresent(customer);
        if (customerIdFromDb != null) {
            customer.setId(customerIdFromDb);
            logger.info("Customer already present in db with id : " + customerIdFromDb);
        } else {
            customer = customerService.saveCustomer(customer);
        }
        Order order = new Order(orderDTO.getOrderDescription(), customer, orderDTO.getCartItems(),
                amount, orderDTO.getCoupon());
        order = orderService.saveOrder(order);
        logger.info("Order processed successfully..");
        responseOrderDTO.setAmount(amount);
        responseOrderDTO.setDate(DateUtil.getCurrentDateTime());
        responseOrderDTO.setInvoiceNumber(new Random().nextInt(1000));
        responseOrderDTO.setOrderId(order.getId());
        responseOrderDTO.setOrderDescription(orderDTO.getOrderDescription());
        responseOrderDTO.setCoupon(orderDTO.getCoupon());

        logger.info("test push..");
        amount =0;
        return ResponseEntity.ok(responseOrderDTO);
    }
}

package com.samrt.dubai.book.store.controller;


import com.samrt.dubai.book.store.entity.Coupon;
import com.samrt.dubai.book.store.repository.CouponRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


@RestController
@RequestMapping("/api/coupon")
public class CouponController {

    @Autowired
    private CouponRepository couponRepository;

    Logger logger = LoggerFactory.getLogger(ShoppingCartController.class);

    @PostMapping("/createCoupon")
    public ResponseEntity<?> createCoupon (@RequestBody Coupon coupon)
    {
        try {
            logger.info("Request Payload " + coupon.toString());
            Optional<Coupon> isFound = couponRepository.findByClassification(coupon.getClassification());
            Coupon responseCoupon = new Coupon();
            if (isFound.isPresent()) {
                return new ResponseEntity<>(HttpStatus.FOUND);
            } else {
                String randomCoupon = generateCoupon("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
                Coupon save=  couponRepository.save(new Coupon(coupon.getDiscount(),randomCoupon,
                        coupon.getClassification(), coupon.isCheckAvailable()));
                logger.info("Coupon saved into database with code: " + randomCoupon);

                responseCoupon.setCouponCode(randomCoupon);
                responseCoupon.setClassification(coupon.getClassification());
                responseCoupon.setId(save.getId());
                responseCoupon.setDiscount(coupon.getDiscount());
                responseCoupon.setCheckAvailable(coupon.isCheckAvailable());

                return ResponseEntity.ok(responseCoupon);
            }
        }catch (Exception exception)
        {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Coupon Classification already Exist!");
        }
    }

    public String generateCoupon(String classification) {
        classification = classification + "0123456789";
        StringBuilder subRandom = new StringBuilder(7);

        for (int i = 0; i < 7; i++) {
            classification= classification.toUpperCase();
            int index = (int) (classification.length() * Math.random());
            subRandom.append(classification
                    .charAt(index));
        }
        return subRandom.toString();
    }
}

package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Serializable {

    private long id;
    private User user;
    private GiftCertificate giftCertificate;
    private BigDecimal price;
    private LocalDateTime dateTime;
}

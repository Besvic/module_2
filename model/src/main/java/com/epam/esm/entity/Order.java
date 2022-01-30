package com.epam.esm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private long id;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user = User.builder().build();

//    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.LAZY)
//    @JoinTable(name = "order_certificate",
//    joinColumns = @JoinColumn(name = "order_id"),
//    inverseJoinColumns = @JoinColumn(name = "certificate_id"))
    @OneToMany(mappedBy = "order", cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private List<GiftCertificate> certificateList;

    private BigDecimal cost;

    @Column(name = "date")
    private LocalDateTime dateTime;
}

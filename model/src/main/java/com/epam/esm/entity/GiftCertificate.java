package com.epam.esm.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name = "gift_certificate")
public class GiftCertificate /*extends RepresentationModel<GiftCertificate>*/ implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "certificate_id")
    private long id;
    @Pattern(regexp = "[a-zA-zа-яА-Я\\s]+", message = "Name may contain only letters. You input: ${validatedValue}")
    private String name;
    @Pattern(regexp = "[a-zA-zа-яА-Я\\s]+", message = "Description may contain only letters. You input: ${validatedValue}")
    private String description;
    @Min(value = 0, message = "Price can not be less then 0. You input: ${validatedValue}")
    private BigDecimal price;
    @Min(value = 0, message = "Duration can not be less then 0. You input: ${validatedValue}")
    private int duration;
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime createDate;
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime lastUpdateDate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinTable( name = "gift_certificate_tag_list",
        joinColumns = @JoinColumn(name = "certificate_list_certificate_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_list_tag_id"))
    private List<Tag> tagList = new ArrayList<>();

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "order_id")
    private Order order;


    public GiftCertificate() {

    }

    @Override
    public String toString() {
        return "GiftCertificate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", duration=" + duration +
                ", createDate=" + createDate +
                ", lastUpdateDate=" + lastUpdateDate +
                ", tagList=" + tagList.toString() +
                '}';
    }
}

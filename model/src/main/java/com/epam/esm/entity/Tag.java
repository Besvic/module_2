package com.epam.esm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.List;

@Data
//@AllArgsConstructor
@Entity
@Table(name = "tag")
public class Tag implements Serializable {

    public Tag() {
    }

    public Tag(long id, @NotBlank(message = "Name can not be empty") @Pattern(regexp = "[a-zA-zа-яА-Я\\s]+", message = "Name may contain only letters. You input: ${validatedValue}") String name) {
        this.id = id;
        this.name = name;
    }

    public Tag(@NotBlank(message = "Name can not be empty")
               @Pattern(regexp = "[a-zA-zа-яА-Я\\s]+", message = "Name may contain only letters. You input: ${validatedValue}")
                       String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private long id;
    @NotBlank(message = "Name can not be empty")
    @Pattern(regexp = "[a-zA-zа-яА-Я\\s]+", message = "Name may contain only letters. You input: ${validatedValue}")
    private String name;


    @JsonIgnore
    @ManyToMany(mappedBy = "tagList", fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})

   /* @JoinTable( name = "gift_certification_tag",
            joinColumns = @JoinColumn(name = "id_tag"),
            inverseJoinColumns = @JoinColumn(name = "id_gift_certification"))*/
    private List<GiftCertificate> certificateList;

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

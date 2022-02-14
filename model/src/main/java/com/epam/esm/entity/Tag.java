package com.epam.esm.entity;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

/**
 * The type Tag.
 */
@Data
@Entity
@Table(name = "tag")
public class Tag implements Serializable {

    /**
     * Instantiates a new Tag.
     */
    public Tag() {
    }

    /**
     * Instantiates a new Tag.
     *
     * @param id   the id
     * @param name the name
     */
    public Tag(long id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Instantiates a new Tag.
     *
     * @param name the name
     */
    public Tag(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private long id;
    private String name;
    @ManyToMany(mappedBy = "tagList", fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    private List<GiftCertificate> certificateList;
}

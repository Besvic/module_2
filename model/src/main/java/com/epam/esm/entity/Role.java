package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

/**
 * The type Role.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user_role")
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @Transient
    @ManyToMany(mappedBy = "roleSet")
    private List<User> userList;

    /**
     * Instantiates a new Role.
     *
     * @param id   the id
     * @param name the name
     */
    public Role(long id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Instantiates a new Role.
     *
     * @param id the id
     */
    public Role(long id) {
        this.id = id;
    }

}

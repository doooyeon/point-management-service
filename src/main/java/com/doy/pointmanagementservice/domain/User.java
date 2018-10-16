package com.doy.pointmanagementservice.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @Id
    private String id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "writer")
    private List<Review> reviews = new ArrayList<>(); ;

    @OneToMany(mappedBy = "user")
    private List<Point> points = new ArrayList<>();;
}

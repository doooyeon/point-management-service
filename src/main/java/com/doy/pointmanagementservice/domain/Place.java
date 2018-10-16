package com.doy.pointmanagementservice.domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class Place {
    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "place")
    private List<Review> reviews;
}

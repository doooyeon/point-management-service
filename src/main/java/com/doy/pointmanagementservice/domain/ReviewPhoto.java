package com.doy.pointmanagementservice.domain;

import javax.persistence.*;

@Entity
public class ReviewPhoto {
    @Id
    private String id;

    @Column(nullable = false)
    private String photoUrl;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;
}

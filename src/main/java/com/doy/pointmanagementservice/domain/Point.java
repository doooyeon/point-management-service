package com.doy.pointmanagementservice.domain;

import com.doy.pointmanagementservice.support.PointType;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private PointType type;

    @Column(nullable = false)
    private int value;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;

    public Point(PointType type, int value, User user, Review review) {
        this.type = type;
        this.value = value;
        this.user = user;
        this.review = review;
    }
}

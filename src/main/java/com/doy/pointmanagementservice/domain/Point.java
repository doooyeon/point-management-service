package com.doy.pointmanagementservice.domain;

import com.doy.pointmanagementservice.support.PointType;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "point",
        indexes = {@Index(name = "review_point_index", columnList = "review_id", unique = true),
                @Index(name = "user_point_index", columnList = "user_id", unique = true)})
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

    public Point(PointType type, int value) {
        this.type = type;
        this.value = value;
    }

    public void addUser(User user) {
        this.user = user;
    }

    public void addReview(Review review) {
        this.review = review;
    }
}

package com.doy.pointmanagementservice.domain;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Review {
    @Id
    private String id;

    @Column(nullable = false)
    @CreatedDate
    private LocalDateTime registerDatetime;

    @Column(nullable = false)
    @LastModifiedDate
    private LocalDateTime updateDatetime;

    @Column(nullable = false)
    private String content;

    @OneToMany(mappedBy = "review")
    private List<ReviewPhoto> reviewPhotos = new ArrayList<>();

    @ColumnDefault(value = "false")
    private boolean deleted;

    @ManyToOne
    @JoinColumn(name = "writer_id")
    private User writer;

    @ManyToOne
    @JoinColumn(name = "place_id")
    private Place place;
}

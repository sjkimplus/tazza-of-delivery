package com.sparta.tazzaofdelivery.domain.favorite.entity;


import com.sparta.tazzaofdelivery.domain.store.entity.Store;
import com.sparta.tazzaofdelivery.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.objenesis.SpringObjenesis;

import java.time.LocalTime;

@Entity
@Getter
@Setter
@Table(name = "favorite")
@NoArgsConstructor
public class Favorite {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorite_id")
    private Long favoriteId;

    @Column(name="created_at")
    private LocalTime createdAt;

    @Column(name="deleted_at")
    private LocalTime deletedAt;

    @Column(nullable = false)
    private Boolean isDeleted = false;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="store_id", nullable = false)
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    public Favorite(User user, Store store) {
        this.user = user;
        this.store = store;
    }
}

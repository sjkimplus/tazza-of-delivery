package com.sparta.tazzaofdelivery.domain.store.entity;

import com.sparta.tazzaofdelivery.domain.menu.entity.Menu;
import com.sparta.tazzaofdelivery.domain.order.entity.Order;
import com.sparta.tazzaofdelivery.domain.store.enums.StoreStatus;
import com.sparta.tazzaofdelivery.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="Store")
@NoArgsConstructor
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long storeId;

    @Column(name = "store_name")
    private String storeName;

    @Column(name = "created_at")
    private LocalTime createdAt;

    @Column(name = "closed_at")
    private LocalTime closedAt;

    @Column(name = "minimum_order_quantity")
    private Long minimumOrderQuantity;

    @Column(name = "store_announcement")
    private String storeAnnouncement;

    @Enumerated(EnumType.STRING)
    @Column(name = "store_status")
    private StoreStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "store", cascade = CascadeType.REMOVE)
    private List<Menu> menus = new ArrayList<>();


    @OneToMany(mappedBy="store")
    private List<Order> orders = new ArrayList<>();


    public Store(String storeName, LocalTime createdAt, LocalTime closedAt, Long minimumOrderQuantity, String storeAnnouncement, User user) {
        this.storeName = storeName;
        this.createdAt = createdAt;
        this.closedAt = closedAt;
        this.minimumOrderQuantity = minimumOrderQuantity;
        this.storeAnnouncement = storeAnnouncement;
        this.user = user;
    }
}
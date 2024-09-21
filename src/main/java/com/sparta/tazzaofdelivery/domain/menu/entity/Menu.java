package com.sparta.tazzaofdelivery.domain.menu.entity;

import com.sparta.tazzaofdelivery.domain.menu.dto.request.MenuSaveRequest;
import com.sparta.tazzaofdelivery.domain.menu.dto.request.MenuUpdateRequest;
import com.sparta.tazzaofdelivery.domain.store.entity.Store;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE menus SET is_deleted = true, deleted_at = CURRENT_TIMESTAMP WHERE menu_id = ?")
@Table(name = "menus")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    Long menuId;

    @Column(name = "menu_name", nullable = false)
    private String menuName;

    @Column(name = "price", nullable = false)
    private int price;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Category category;

    @Column(name = "is_deleted")
    private boolean isDeleted = Boolean.FALSE;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public Menu(MenuSaveRequest request) {
        this.menuName = request.getMenuName();
        this.price = request.getPrice();
        this.category = request.getCategory();
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    public void update(MenuUpdateRequest request) {

        if(request.getMenuName() != null){
            this.menuName = request.getMenuName();
        }

        if(request.getPrice() != null){
            this.price = request.getPrice();
        }

        if(request.getCategory() != null){
            this.category = request.getCategory();
        }
    }
}

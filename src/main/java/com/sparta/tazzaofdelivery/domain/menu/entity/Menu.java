package com.sparta.tazzaofdelivery.domain.menu.entity;

import com.sparta.tazzaofdelivery.domain.exception.ErrorCode;
import com.sparta.tazzaofdelivery.domain.exception.TazzaException;
import com.sparta.tazzaofdelivery.domain.menu.dto.request.MenuSaveRequest;
import com.sparta.tazzaofdelivery.domain.menu.dto.request.MenuUpdateRequest;
import com.sparta.tazzaofdelivery.domain.store.entity.Store;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE menus SET is_deleted = true, deleted_at = CURRENT_TIMESTAMP WHERE menu_id = ?")
@Table(name = "menus")
@EntityListeners(AuditingEntityListener.class)
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

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public Menu(MenuSaveRequest request, Store store) {
        this.menuName = request.getMenuName();
        this.price = request.getPrice();
        this.category = request.getCategory();
        this.store = store;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;


    public void update(MenuUpdateRequest request) {

        if(request.getMenuName() != null){
            this.menuName = request.getMenuName();
        }

        if(request.getPrice() != null){
            if(request.getPrice() > 0){
                this.price = request.getPrice();
            }else throw new TazzaException(ErrorCode.MENU_INVALID_PRICE);

        }

        if(request.getCategory() != null){
            this.category = request.getCategory();
        }
    }
}

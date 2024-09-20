package com.sparta.tazzaofdelivery.domain.menu.entity;

<<<<<<< HEAD
import com.sparta.tazzaofdelivery.domain.menu.dto.request.MenuSaveRequest;
=======
//import com.sparta.tazzaofdelivery.domain.menu.dto.request.MenuSaveRequest;
>>>>>>> 39e584c084be50d0c16004ef5fd33a4348b453da
import com.sparta.tazzaofdelivery.domain.store.entity.Store;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "menu_name", nullable = false)
    private String menuName;

    @Column(name = "price", nullable = false)
    private int price;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Category category;

<<<<<<< HEAD
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    public Menu(MenuSaveRequest request) {
        this.menuName = request.getMenuName();
        this.price = request.getPrice();
        this.category = request.getCategory();
    }
=======
//    public Menu(MenuSaveRequest request) {
//        this.menuName = request.getMenuName();
//        this.price = request.getPrice();
//        this.category = request.getCategory();
//    }
>>>>>>> 39e584c084be50d0c16004ef5fd33a4348b453da



}

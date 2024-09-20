package com.sparta.tazzaofdelivery.domain.menu.entity;

//import com.sparta.tazzaofdelivery.domain.menu.dto.request.MenuSaveRequest;
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

//    public Menu(MenuSaveRequest request) {
//        this.menuName = request.getMenuName();
//        this.price = request.getPrice();
//        this.category = request.getCategory();
//    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

}

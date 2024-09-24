package com.sparta.tazzaofdelivery.domain.order.service;

import com.sparta.tazzaofdelivery.domain.cart.entity.Cart;
import com.sparta.tazzaofdelivery.domain.cart.entity.CartStatus;
import com.sparta.tazzaofdelivery.domain.cart.repository.CartRepository;
import com.sparta.tazzaofdelivery.domain.exception.ErrorCode;
import com.sparta.tazzaofdelivery.domain.exception.TazzaException;
import com.sparta.tazzaofdelivery.domain.menu.entity.Menu;
import com.sparta.tazzaofdelivery.domain.menu.repository.MenuRepository;
import com.sparta.tazzaofdelivery.domain.order.dto.request.OrderCreateRequest;
import com.sparta.tazzaofdelivery.domain.order.dto.response.OrderByOwnerResponse;
import com.sparta.tazzaofdelivery.domain.order.dto.response.OrderByUserResponse;
import com.sparta.tazzaofdelivery.domain.order.dto.response.OrderCreateResponse;
import com.sparta.tazzaofdelivery.domain.order.dto.response.OrderStatusResponse;
import com.sparta.tazzaofdelivery.domain.order.entity.Order;
import com.sparta.tazzaofdelivery.domain.order.orderconfig.OrderStatus;
import com.sparta.tazzaofdelivery.domain.order.repository.OrderRepository;
import com.sparta.tazzaofdelivery.domain.store.entity.Store;
import com.sparta.tazzaofdelivery.domain.store.repository.StoreRepository;
import com.sparta.tazzaofdelivery.domain.user.entity.AuthUser;
import com.sparta.tazzaofdelivery.domain.user.entity.User;
import com.sparta.tazzaofdelivery.domain.user.repository.UserRepository;
import com.sparta.tazzaofdelivery.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;
    private final CartRepository cartRepository;

    public OrderCreateResponse createOrder(
            AuthUser authUser,
            OrderCreateRequest orderCreateRequest) {
        // 주문한 장바구니 찾기
        Cart cart = findCartByCartId(orderCreateRequest.getCartId());

        // 주문한 menu 찾기
        Menu orderMenu = checkMenu(cart.getMenuId());

        // 사용자 찾기
        User orderUser = findUserByUserId(authUser.getId());

        // 주문 가게
        Store orderStore = checkStore(cart.getStoreId());

        LocalTime now = LocalTime.now();
        if(now.isBefore(orderStore.getCreatedAt()) || now.isAfter(orderStore.getClosedAt())){
            throw new TazzaException(ErrorCode.STORE_NOT_OPEN);
        }

        // cart 상태 주문됨으로 변경
        cart.updateStatus(CartStatus.ORDERED);

        // 주문 금액
        Double totalPrice = orderMenu.getPrice() * cart.getMenuCount().doubleValue();
        // 메뉴 금액
        Double menuPrice = (double) orderMenu.getPrice();
        System.out.println(":::: 메뉴 가격 ::::"+menuPrice);

        if(totalPrice<orderStore.getMinimumOrderQuantity()){
            throw new TazzaException(ErrorCode.ORDER_FORBIDDEN);
        }



        Order newOrder = new Order(
                totalPrice,
                LocalDateTime.now(),
                OrderStatus.WAIT_APPROVE,
                false,
                orderMenu.getMenuName(),
                menuPrice,
                cart.getMenuCount(),
                orderUser,
                orderStore
        );

        Order saveOrder = orderRepository.save(newOrder);

        return OrderCreateResponse.builder()
                .orderId(saveOrder.getOrderId())
                .totalPrice(saveOrder.getTotalPrice())
                .orderCreatedAt(saveOrder.getOrderCreatedAt())
                .orderStatus(saveOrder.getOrderStatus())
                .menuName(saveOrder.getMenuName())
                .menuCount(saveOrder.getMenuCount())
                .menuPrice(saveOrder.getMenuPrice())
                .user(saveOrder.getUser())
                .store(saveOrder.getStore())
                .isReview(saveOrder.getIsReview())
                .build();
    }

    // 주문 수락
    public OrderStatusResponse approveOrder(AuthUser authUser, Long orderId) {
        Order order = orderUserAuthentication(authUser.getId(), orderId);
        if(OrderStatus.PREPARE.getOrderCode() > order.getOrderStatus().getOrderCode()){
            throw new TazzaException(ErrorCode.ORDER_STATUS_FORBIDDEN);
        } else {
            order.approve(OrderStatus.PREPARE);
        }
        return OrderStatusResponse.builder()
                .orderId(order.getOrderId())
                .orderStatus(order.getOrderStatus())
                .build();
    }

    // 배달 시작
    public OrderStatusResponse deliverOrder(AuthUser authUser, Long orderId) {
        Order order = orderUserAuthentication(authUser.getId(), orderId);
        if(OrderStatus.DELIVERY.getOrderCode() > order.getOrderStatus().getOrderCode()){
            throw new TazzaException(ErrorCode.ORDER_STATUS_FORBIDDEN);
        } else {
            order.approve(OrderStatus.DELIVERY);
        }
        return OrderStatusResponse.builder()
                .orderId(order.getOrderId())
                .orderStatus(order.getOrderStatus())
                .build();
    }

    // 배달 완료
    public OrderStatusResponse completeOrder(AuthUser authUser, Long orderId) {
        Order order = orderUserAuthentication(authUser.getId(), orderId);
        if(OrderStatus.COMPLETE.getOrderCode() > order.getOrderStatus().getOrderCode()){
            throw new TazzaException(ErrorCode.ORDER_STATUS_FORBIDDEN);
        } else {
            order.approve(OrderStatus.COMPLETE);
        }
        return OrderStatusResponse.builder()
                .orderId(order.getOrderId())
                .orderStatus(order.getOrderStatus())
                .build();
    }

    // user가 주문한 내역 조회
    public Page<OrderByUserResponse> getAllUserOrder(AuthUser authUser, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page-1,size);

        User user = findUserByUserId(authUser.getId());

        Page<Order> userOrderList = orderRepository.findByUser(user, pageable)
                .orElseThrow(()-> new TazzaException(ErrorCode.USER_ORDER_NOT_EXIST));

//        if(userOrderList.isEmpty()) {
//            throw new TazzaException(ErrorCode.USER_ORDER_NOT_EXIST);
//        }

        return userOrderList
                .map(order -> OrderByUserResponse.builder()
                        .menuName(order.getMenuName())
                        .menuPrice(order.getMenuPrice())
                        .menuCount(order.getMenuCount())
                        .orderCreatedAt(order.getOrderCreatedAt())
                        .totalPrice(order.getTotalPrice())
                        .store(order.getStore())
                        .orderStatus(order.getOrderStatus())
                        .build());

    }

    // 가게에서 들어온 주문내역 조회
    public Page<OrderByOwnerResponse> getAllOwnerOrder(Long storeId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page-1,size);

        Store store = checkStore(storeId);

        Page<Order> ownerOrderList = orderRepository.findByStore(store,pageable)
                .orElseThrow(()-> new TazzaException(ErrorCode.OWNER_ORDER_NOT_EXIST));

        return ownerOrderList
                .map(order -> OrderByOwnerResponse.builder()
                        .menuName(order.getMenuName())
                        .menuPrice(order.getMenuPrice())
                        .menuCount(order.getMenuCount())
                        .orderCreatedAt(order.getOrderCreatedAt())
                        .totalPrice(order.getTotalPrice())
                        .orderStatus(order.getOrderStatus())
                        .user(order.getUser())
                        .build());
    }




    // 주문한 사용자와 로그인한 사용자가 동일한지 검증
    private Order orderUserAuthentication(Long userId, Long orderId) {
        User user = findUserByUserId(userId);
        Order order = findOrderByOrderId(orderId);

        if(!order.getUser().getUserId().equals(user.getUserId())) {
            throw new TazzaException(ErrorCode.ORDER_USER_NOT_EQUAL);
        }
        return order;
    }

    // 주문 확인
    private Order findOrderByOrderId(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(()-> new TazzaException(ErrorCode.ORDER_NOT_FOUND));
    }


    // 장바구니 확인
    private Cart findCartByCartId(Long cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(()-> new TazzaException(ErrorCode.CART_NOT_FOUND));
    }

    // 사용자 확인
    private User findUserByUserId(long id) {
        return userRepository.findById(id)
                .orElseThrow(()->new TazzaException(ErrorCode.USER_NOT_FOUND));
    }

    // 메뉴 확인
    private Menu checkMenu(Long menuId) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new TazzaException(ErrorCode.MENU_NOT_FOUND));
        if(menu.isDeleted()){
            throw new TazzaException(ErrorCode.MENU_ISDELETED);
        }
        return menu;
    }

    // 가게 확인
    private Store checkStore(Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new TazzaException(ErrorCode.STORE_NOT_FOUND));
        return store;
    }


}

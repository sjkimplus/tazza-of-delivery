package com.sparta.tazzaofdelivery.domain.menu.service;

import com.sparta.tazzaofdelivery.domain.exception.TazzaException;
import com.sparta.tazzaofdelivery.domain.menu.dto.request.MenuSaveRequest;
import com.sparta.tazzaofdelivery.domain.menu.dto.request.MenuUpdateRequest;
import com.sparta.tazzaofdelivery.domain.menu.entity.Category;
import com.sparta.tazzaofdelivery.domain.menu.entity.Menu;
import com.sparta.tazzaofdelivery.domain.menu.repository.MenuRepository;
import com.sparta.tazzaofdelivery.domain.store.entity.Store;
import com.sparta.tazzaofdelivery.domain.store.enums.StoreStatus;
import com.sparta.tazzaofdelivery.domain.store.repository.StoreRepository;
import com.sparta.tazzaofdelivery.domain.user.entity.AuthUser;
import com.sparta.tazzaofdelivery.domain.user.entity.User;
import com.sparta.tazzaofdelivery.domain.user.enums.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.*;

@SpringBootTest
class MenuServiceTest2 {

    @MockBean
    private MenuRepository menuRepository;

    @MockBean
    private StoreRepository storeRepository;

    @InjectMocks
    private MenuService menuService;

    @Test
    void 메뉴_생성_성공() {
        // given
        AuthUser authUser = new AuthUser(1L, UserType.OWNER);
        User user = User.fromAuthUser(authUser);

        Store store = new Store("인생치킨", LocalTime.parse("09:00:00"), LocalTime.parse("20:00:00"), 12000L, "공지사항", StoreStatus.ACTIVE, user);
        // Mock behavior for storeRepository
        when(storeRepository.findById(1L)).thenReturn(Optional.of(store));

        MenuSaveRequest saveRequest = new MenuSaveRequest("후라이드치킨", 15000, Category.CHICKEN);

        // when
        menuService.createMenu(1L, authUser, saveRequest);

        // then
        ArgumentCaptor<Menu> menuCaptor = forClass(Menu.class);
        verify(menuRepository, times(1)).save(menuCaptor.capture());

        Menu savedMenu = menuCaptor.getValue();

        assertEquals("후라이드치킨", savedMenu.getMenuName());
        assertEquals(15000, savedMenu.getPrice());
        assertEquals(Category.CHICKEN, savedMenu.getCategory());
        assertEquals(store, savedMenu.getStore());
    }

    @Test
    void 메뉴_생성_실패_메뉴생성권한이없는경우() {
        // given
        AuthUser authUser1 = new AuthUser(1L, UserType.OWNER);
        User owner = User.fromAuthUser(authUser1);
        AuthUser authUser2 = new AuthUser(2L, UserType.OWNER);

        Store store = new Store("인생치킨", LocalTime.parse("09:00:00"), LocalTime.parse("20:00:00"), 12000L, "공지사항", StoreStatus.ACTIVE, owner);
        when(storeRepository.findById(1L)).thenReturn(Optional.of(store));

        MenuSaveRequest saveRequest = new MenuSaveRequest("후라이드치킨", 15000, Category.CHICKEN);

        // when & then
        TazzaException exception = assertThrows(TazzaException.class,
                () -> menuService.createMenu(1L, authUser2, saveRequest));
        assertEquals("메뉴 등록 및 수정은 가게 사장님만 가능합니다.", exception.getMessage());
    }

    @Test
    void 메뉴_수정_성공() {
        // given
        AuthUser authUser = new AuthUser(1L, UserType.OWNER);
        User user = User.fromAuthUser(authUser);

        Store store = new Store("인생치킨", LocalTime.parse("09:00:00"), LocalTime.parse("20:00:00"), 12000L, "공지사항", StoreStatus.ACTIVE, user);

        MenuUpdateRequest updateRequest = new MenuUpdateRequest("불고기피자", 18000, Category.PIZZA);

        MenuSaveRequest saveRequest = new MenuSaveRequest("후라이드치킨", 15000, Category.CHICKEN);
        Menu menu = new Menu(saveRequest, store);
        ReflectionTestUtils.setField(menu, "menuId", 1L);
        menuRepository.save(menu);
        when(menuRepository.findById(1L)).thenReturn(Optional.of(menu));

        // when
        menuService.updateMenu(1L, authUser, updateRequest);

        // then
        assertEquals("불고기피자", menu.getMenuName());
        assertEquals(18000, menu.getPrice());
        assertEquals(Category.PIZZA, menu.getCategory());
    }

    @Test
    void 메뉴_수정_실패_메뉴수정권한이없는경우() {
        // given
        AuthUser authUser1 = new AuthUser(1L, UserType.OWNER);
        User owner = User.fromAuthUser(authUser1);
        AuthUser authUser2 = new AuthUser(2L, UserType.OWNER);

        Store store = new Store("인생치킨", LocalTime.parse("09:00:00"), LocalTime.parse("20:00:00"), 12000L, "공지사항", StoreStatus.ACTIVE, owner);

        MenuUpdateRequest updateRequest = new MenuUpdateRequest("불고기피자", 18000, Category.PIZZA);

        MenuSaveRequest saveRequest = new MenuSaveRequest("후라이드치킨", 15000, Category.CHICKEN);
        Menu menu = new Menu(saveRequest, store);
        when(menuRepository.findById(1L)).thenReturn(Optional.of(menu));

        // when & then
        TazzaException exception = assertThrows(TazzaException.class,
                () -> menuService.updateMenu(1L, authUser2, updateRequest));
        assertEquals("메뉴 등록 및 수정은 가게 사장님만 가능합니다.", exception.getMessage());
    }

    @Test
    void 메뉴_수정_실패_등록되지않은메뉴() {
        // given
        AuthUser authUser1 = new AuthUser(1L, UserType.OWNER);

        Menu menu = new Menu();
        when(menuRepository.findById(1L)).thenReturn(Optional.of(menu));

        MenuUpdateRequest updateRequest = new MenuUpdateRequest("불고기피자", 18000, Category.PIZZA);

        // when & then
        TazzaException exception = assertThrows(TazzaException.class,
                () -> menuService.updateMenu(2L, authUser1, updateRequest));
        assertEquals("등록되지 않은 메뉴입니다.", exception.getMessage());
    }

    @Test
    void 메뉴_수정_실패_삭제된메뉴() {
        // given
        AuthUser authUser1 = new AuthUser(1L, UserType.OWNER);

        Menu menu = new Menu();
        ReflectionTestUtils.setField(menu, "isDeleted", true);
        when(menuRepository.findById(1L)).thenReturn(Optional.of(menu));

        MenuUpdateRequest updateRequest = new MenuUpdateRequest("불고기피자", 18000, Category.PIZZA);

        // when & then
        TazzaException exception = assertThrows(TazzaException.class,
                () -> menuService.updateMenu(1L, authUser1, updateRequest));
        assertEquals("삭제된 메뉴입니다.", exception.getMessage());
    }

    @Test
    void 메뉴_수정_실패_가격입력오류() {
        // given
        AuthUser authUser = new AuthUser(1L, UserType.OWNER);
        User user = User.fromAuthUser(authUser);

        Store store = new Store("인생치킨", LocalTime.parse("09:00:00"), LocalTime.parse("20:00:00"), 12000L, "공지사항", StoreStatus.ACTIVE, user);

        MenuUpdateRequest updateRequest = new MenuUpdateRequest("불고기피자", -18000, Category.PIZZA);

        MenuSaveRequest saveRequest = new MenuSaveRequest("후라이드치킨", 15000, Category.CHICKEN);
        Menu menu = new Menu(saveRequest, store);
        ReflectionTestUtils.setField(menu, "menuId", 1L);
        menuRepository.save(menu);
        when(menuRepository.findById(1L)).thenReturn(Optional.of(menu));

        // when & then
        TazzaException exception = assertThrows(TazzaException.class,
                () -> menuService.updateMenu(1L, authUser, updateRequest));
        assertEquals("가격은 0이상의 양수만 입력가능합니다.", exception.getMessage());
    }

    @Test
    void 메뉴_삭제_성공() {
        // given
        AuthUser authUser = new AuthUser(1L, UserType.OWNER);
        User user = User.fromAuthUser(authUser);

        Store store = new Store("인생치킨", LocalTime.parse("09:00:00"), LocalTime.parse("20:00:00"), 12000L, "공지사항", StoreStatus.ACTIVE, user);

        MenuSaveRequest saveRequest = new MenuSaveRequest("후라이드치킨", 15000, Category.CHICKEN);
        Menu menu = new Menu(saveRequest, store);
        ReflectionTestUtils.setField(menu, "menuId", 1L);
        when(menuRepository.findById(1L)).thenReturn(Optional.of(menu));

        // when
        menuService.deleteMenu(1L, authUser);

        // then
        verify(menuRepository, times(1)).delete(menu);
        assertTrue(menu.isDeleted());

    }
}
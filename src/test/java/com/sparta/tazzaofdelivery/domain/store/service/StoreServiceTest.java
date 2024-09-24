package com.sparta.tazzaofdelivery.domain.store.service;

import com.sparta.tazzaofdelivery.domain.exception.ErrorCode;
import com.sparta.tazzaofdelivery.domain.exception.TazzaException;
import com.sparta.tazzaofdelivery.domain.menu.dto.request.MenuSaveRequest;
import com.sparta.tazzaofdelivery.domain.menu.dto.response.MenuSaveResponse;
import com.sparta.tazzaofdelivery.domain.menu.entity.Category;
import com.sparta.tazzaofdelivery.domain.menu.entity.Menu;
import com.sparta.tazzaofdelivery.domain.search.entity.Search;
import com.sparta.tazzaofdelivery.domain.search.service.SearchService;
import com.sparta.tazzaofdelivery.domain.store.dto.request.StoreCreateRequest;
import com.sparta.tazzaofdelivery.domain.store.dto.response.StoreCreateResponse;
import com.sparta.tazzaofdelivery.domain.store.dto.response.StoreGetAllResponse;
import com.sparta.tazzaofdelivery.domain.store.dto.response.StoreGetResponse;
import com.sparta.tazzaofdelivery.domain.store.dto.response.StoreIntegratedResponse;
import com.sparta.tazzaofdelivery.domain.store.entity.Store;
import com.sparta.tazzaofdelivery.domain.store.enums.StoreStatus;
import com.sparta.tazzaofdelivery.domain.store.repository.StoreRepository;
import com.sparta.tazzaofdelivery.domain.user.entity.AuthUser;
import com.sparta.tazzaofdelivery.domain.user.entity.User;
import com.sparta.tazzaofdelivery.domain.user.enums.UserType;
import com.sparta.tazzaofdelivery.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StoreServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private StoreRepository storeRepository;
    @Mock
    private SearchService searchService;

    @InjectMocks
    private StoreService storeService;


    /*
    * Create Store
    * 1. Store 정상 생성
    * 2. 유저 예외처리
    * 3. OWNER 권한 아닌 경우 예외처리
    * 4. OWNER 인 사람이 Store 생성 3개 이상 시 예외처리
    * */
    @Test
    void Store_정상_생성() {
        // given
        AuthUser authUser = new AuthUser(1L, UserType.OWNER);
        User user = new User(authUser.getId(), authUser.getUserRole());

        given(userRepository.findById(user.getUserId())).willReturn(Optional.of(user));
        given(storeRepository.countByUserAndStatus(user,StoreStatus.ACTIVE)).willReturn(1L);

        StoreCreateRequest request = new StoreCreateRequest("아아", LocalTime.now(), LocalTime.now().plusHours(10),1000L, "오늘의 가게 공지", StoreStatus.ACTIVE);
        Store store = new Store(request.getStoreName(), request.getCreatedAt(), request.getClosedAt(), request.getMinimumOrderQuantity(), request.getStoreAnnouncement(), request.getStoreStatus(), user);
        given(storeRepository.save(any(Store.class))).willReturn(store);

        // when & then
        StoreCreateResponse response = storeService.createStore(request, authUser);

        assertNotNull(response);
        assertEquals(request.getStoreName(),response.getStoreName());
        assertEquals(request.getCreatedAt(),response.getCreatedAt());
        assertEquals(request.getClosedAt(),response.getClosedAt());
        assertEquals(request.getMinimumOrderQuantity(),response.getMinimumOrderQuantity());
        assertEquals(request.getStoreAnnouncement(),response.getStoreAnnouncement());
        assertEquals(request.getStoreStatus(),response.getStoreStatus());
        verify(storeRepository, times(1)).save(any(Store.class));
    }

    @Test
    void Store_생성시_유저_존재하지_않는_예외처리() {
        // given
        AuthUser authUser = new AuthUser(1L, UserType.OWNER);
        User user = new User(authUser.getId(), authUser.getUserRole());
        given(userRepository.findById(user.getUserId())).willReturn(Optional.empty());

        StoreCreateRequest request = new StoreCreateRequest("아아", LocalTime.now(), LocalTime.now().plusHours(10),1000L, "오늘의 가게 공지", StoreStatus.ACTIVE);

        // when & then
        TazzaException exception = assertThrows(TazzaException.class, () -> {
            storeService.createStore(request, authUser);
        });

        assertEquals(ErrorCode.USER_NOT_FOUND, exception.getErrorCode());

    }

    @Test
    void OWNER_아닌_경우_예외처리(){
        // given
        AuthUser authUser = new AuthUser(1L, UserType.CUSTOMER);
        User user = new User(authUser.getId(), authUser.getUserRole());
        given(userRepository.findById(user.getUserId())).willReturn(Optional.of(user));

        StoreCreateRequest request = new StoreCreateRequest("아아", LocalTime.now(), LocalTime.now().plusHours(10),1000L, "오늘의 가게 공지", StoreStatus.ACTIVE);

        // when & then
        TazzaException exception = assertThrows(TazzaException.class, () -> {
            storeService.createStore(request, authUser);
        });

        assertEquals(ErrorCode.STORE_FORBIDDEN, exception.getErrorCode());
        verify(userRepository, times(1)).findById(user.getUserId());
        verify(storeRepository, times(0)).save(any(Store.class));
    }

    @Test
    void Store_생성_실패_가게_최대_수_초과_예외처리(){
        AuthUser authUser = new AuthUser(1L, UserType.OWNER);
        User user = new User(authUser.getId(), authUser.getUserRole());
        given(userRepository.findById(user.getUserId())).willReturn(Optional.of(user));
        given(storeRepository.countByUserAndStatus(user,StoreStatus.ACTIVE)).willReturn(3L);

        StoreCreateRequest request = new StoreCreateRequest("아아", LocalTime.now(), LocalTime.now().plusHours(10),1000L, "오늘의 가게 공지", StoreStatus.ACTIVE);

        // when & then
        TazzaException exception = assertThrows(TazzaException.class, () -> {
            storeService.createStore(request, authUser);
        });

        assertEquals(ErrorCode.STORE_BAD_REQUEST, exception.getErrorCode());
    }

    /*
    * 다건 조회
    * 1. 성공
    * 2. 가게 없을때
    * */
    @Test
    void Store_다건_조회_성공() {
        // given
        Store store1 = new Store("가게1", LocalTime.now(), LocalTime.now().plusHours(10),1000L, "오늘의 가게 공지", StoreStatus.ACTIVE , null);
        Store store2 = new Store("가게2", LocalTime.now(), LocalTime.now().minusHours(20),1000L, "오늘의 가게 공지", StoreStatus.CLOSED , null);

        List<Store> stores = Arrays.asList(store1, store2);
        given(storeRepository.findAll()).willReturn(stores);

        // when
        List<StoreGetAllResponse> responseList = storeService.getAllStores();

        // then
        assertNotNull(responseList);
        assertEquals(2, responseList.size());

        StoreGetAllResponse response1 = responseList.get(0);

        assertEquals("가게1", response1.getStoreName());
        assertEquals(StoreStatus.ACTIVE, response1.getStoreStatus());

        verify(storeRepository, times(1)).findAll();
    }

    @Test
    void Store_다건_조회_가게_없음(){
        // given
        given(storeRepository.findAll()).willReturn(Arrays.asList());

        // when
        List<StoreGetAllResponse> responseList = storeService.getAllStores();

        // then
        assertNotNull(responseList);
        assertTrue(responseList.isEmpty());

        verify(storeRepository, times(1)).findAll();
    }

    /*
    * 단건 조회
    * 1. 단건 조회 성공
    * 2. 조회 실패 가게 없는 예외처리
    * 3. 조회 시 메뉴가 없을때
    * */
    @Test
    void Store_단건_조회_성공(){
        // given
        Store store1 = new Store("가게1", LocalTime.now(), LocalTime.now().plusHours(10),1000L, "오늘의 가게 공지", StoreStatus.ACTIVE , null);
        MenuSaveRequest request = new MenuSaveRequest("메뉴112", 90, Category.CHICKEN);
        MenuSaveRequest request2 = new MenuSaveRequest("메뉴2", 90, Category.CHICKEN);
        Menu menu1 = new Menu(request, store1);
        Menu menu2 = new Menu(request2, store1);

        store1.setMenus(Arrays.asList(menu1, menu2));

        given(storeRepository.findById(store1.getStoreId())).willReturn(Optional.of(store1));

        // when
        StoreGetResponse response = storeService.getStore(store1.getStoreId());

        // then
        assertNotNull(response);
        assertEquals("가게1", response.getStoreName());
        List<MenuSaveResponse> menuSaveResponses = response.getMenus();
        assertEquals(2, menuSaveResponses.size());
        assertEquals("메뉴112", menuSaveResponses.get(0).getMenuName());
        verify(storeRepository, times(1)).findById(store1.getStoreId());
    }

    @Test
    void Store_단건_조회_실패_예외처리(){
        // given
        given(storeRepository.findById(anyLong())).willReturn(Optional.empty());

        // when & then
        TazzaException exception = assertThrows(TazzaException.class, () -> {
            storeService.getStore(anyLong());
        });

        assertEquals(ErrorCode.STORE_NOT_FOUND, exception.getErrorCode());

        verify(storeRepository, times(1)).findById(anyLong());
    }

    @Test
    void Store_단건_조회_메뉴_없을때(){
        // given
        Store store1 = new Store("가게1", LocalTime.now(), LocalTime.now().plusHours(10),1000L, "오늘의 가게 공지", StoreStatus.ACTIVE , null);
        MenuSaveRequest request = new MenuSaveRequest("메뉴112", 90, Category.CHICKEN);
        MenuSaveRequest request2 = new MenuSaveRequest("메뉴2", 90, Category.CHICKEN);
        Menu menu1 = new Menu(request, store1);
        Menu menu2 = new Menu(request2, store1);
        menu1.setDeleted(true);
        menu2.setDeleted(true);

        store1.setMenus(Arrays.asList(menu1, menu2));

        given(storeRepository.findById(store1.getStoreId())).willReturn(Optional.of(store1));

        // when
        StoreGetResponse response = storeService.getStore(store1.getStoreId());

        // then
        assertNotNull(response);
        assertEquals("가게1", response.getStoreName());
        List<MenuSaveResponse> menuSaveResponses = response.getMenus();
        assertTrue(menuSaveResponses.isEmpty());
        verify(storeRepository, times(1)).findById(store1.getStoreId());
    }

    /*
    * 가게 폐업
    * 1. 폐업 성공
    * 2. 폐업할 가게 없을때 예외처리
    * 3. 폐업할 가게의 소유자가 아닐때 예외처리
    * */
    @Test
    void Store_폐업_성공() {
        // given
        AuthUser authUser = new AuthUser(1L, UserType.OWNER);
        User user = new User(authUser.getId(), authUser.getUserRole());
        Store store1 = new Store("가게1", LocalTime.now(), LocalTime.now().plusHours(10),1000L, "오늘의 가게 공지", StoreStatus.ACTIVE , user);

        given(storeRepository.findById(store1.getStoreId())).willReturn(Optional.of(store1));

        // when
        storeService.deleteStore(store1.getStoreId(), authUser);

        // then
        assertEquals(StoreStatus.CLOSED, store1.getStatus());
        verify(storeRepository, times(1)).findById(store1.getStoreId());
    }

    @Test
    void Store_폐업_실패_가게_없음_예외처리(){
        // given
        AuthUser authUser = new AuthUser(1L, UserType.OWNER);
        User user = new User(authUser.getId(), authUser.getUserRole());
        Store store1 = new Store("가게1", LocalTime.now(), LocalTime.now().plusHours(10),1000L, "오늘의 가게 공지", StoreStatus.ACTIVE , user);
        given(storeRepository.findById(store1.getStoreId())).willReturn(Optional.empty());

        // when & then
        TazzaException exception = assertThrows(TazzaException.class, () -> {
            storeService.deleteStore(store1.getStoreId(), authUser);
        });
        assertEquals(ErrorCode.STORE_NOT_FOUND, exception.getErrorCode());
        verify(storeRepository, times(1)).findById(store1.getStoreId());
    }

    @Test
    void Store_폐업_실패_소유자_불일치_예외처리(){
        // given
        AuthUser authUser = new AuthUser(1L, UserType.OWNER);
        AuthUser authUser2 = new AuthUser(2L, UserType.OWNER);
        User user = new User(authUser.getId(), authUser.getUserRole());
        Store store1 = new Store("가게1", LocalTime.now(), LocalTime.now().plusHours(10),1000L, "오늘의 가게 공지", StoreStatus.ACTIVE , user);

        given(storeRepository.findById(store1.getStoreId())).willReturn(Optional.of(store1));

        // when & then
        TazzaException exception = assertThrows(TazzaException.class, () -> {
            storeService.deleteStore(store1.getStoreId(), authUser2);
        });

        assertEquals(ErrorCode.STORE_DELETE_FORBIDDEN, exception.getErrorCode());
        verify(storeRepository, times(1)).findById(store1.getStoreId());
        verify(storeRepository, times(0)).save(store1);
    }

    /*
     * 통합 검색
     * 1. 통합 검색 성공
     * */

    @Test
    void 통합_검색_성공(){
        // given
        Store store1 = new Store("가게1", LocalTime.now(), LocalTime.now().plusHours(10),1000L, "오늘의 가게 공지", StoreStatus.ACTIVE , null);
        Store store2 = new Store("가게1", LocalTime.now(), LocalTime.now().plusHours(10),1000L, "오늘의 가게 공지", StoreStatus.ACTIVE , null);
        List<Store> stores = Arrays.asList(store1, store2);

        given(storeRepository.searchStoresWithMenu("가게", "메뉴", StoreStatus.ACTIVE)).willReturn(stores);

        // when
        List<StoreIntegratedResponse> responseList = storeService.searchStores("가게","메뉴", StoreStatus.ACTIVE);

        // then
        assertNotNull(responseList);
        assertEquals(2, responseList.size());
        assertEquals("가게1", responseList.get(0).getStoreName());

        verify(searchService,times(1)).recordSearchKeyword("가게");
        verify(searchService, times(1)).recordSearchKeyword("메뉴");
        verify(searchService, times(1)).recordSearchKeyword(StoreStatus.ACTIVE.toString());
    }
}
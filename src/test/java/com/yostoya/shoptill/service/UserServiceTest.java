package com.yostoya.shoptill.service;

import com.yostoya.shoptill.domain.Item;
import com.yostoya.shoptill.domain.Role;
import com.yostoya.shoptill.domain.User;
import com.yostoya.shoptill.domain.dto.*;
import com.yostoya.shoptill.exception.alreadyexist.UserAlreadyExistException;
import com.yostoya.shoptill.exception.notfound.UserNotFoundException;
import com.yostoya.shoptill.mapper.ItemMapper;
import com.yostoya.shoptill.mapper.UserMapper;
import com.yostoya.shoptill.repository.ItemRepository;
import com.yostoya.shoptill.repository.UserRepository;
import com.yostoya.shoptill.service.facade.TillFacade;
import com.yostoya.shoptill.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private  UserRepository userRepository;
    @Mock
    private  UserMapper userMapper;
    @Mock
    private  ItemRepository itemRepository;
    @Mock
    private  ItemMapper itemMapper;
    @Mock
    private  TillFacade tillFacade;

    @InjectMocks
    private UserServiceImpl service;

    private User user;
    private UserDto expected;

    private void init() {
        user = new User(null, "f", "l", "e", "p", Role.USER, LocalDateTime.now());
        expected = new UserDto(1L, "f", "l", "e", Role.USER.name(), LocalDateTime.now());
    }

    @Test
    void register() {

        init();

        final RegisterDto registerDto = new RegisterDto("f", "l", "e", "p");
        when(userMapper.toUser(any())).thenReturn(user);
        when(userRepository.save(any())).thenReturn(user);
        when(userMapper.toDto(any())).thenReturn(expected);

        final UserDto actual = service.register(registerDto);

        assertNotNull(actual);
        assertThat(actual).isEqualTo(expected);
        verify(userMapper, times(1)).toUser(registerDto);
        verify(userRepository, times(1)).save(user);
        verify(userMapper, times(1)).toDto(user);
    }

    @Test
    void register_user_exist_exception() {

        when(userMapper.toUser(any())).thenThrow(UserAlreadyExistException.class);
        assertThatExceptionOfType(UserAlreadyExistException.class)
                .isThrownBy(() -> service.register(Mockito.mock(RegisterDto.class)));
    }

    @Test
    void getUserByEmail() {

        init();

        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        when(userMapper.toDto(userRepository.findByEmail(any()).get())).thenReturn(expected);

        final UserDto actual = service.getUserByEmail(any());

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getUserByEmail_notFound_exception() {

        when(userRepository.findByEmail(any())).thenThrow(UserNotFoundException.class);
        assertThatExceptionOfType(UserNotFoundException.class)
                .isThrownBy(() -> service.getUserByEmail(any()));
    }

    @Test
    void tillCheckout() {
        final List<String> itemNames = List.of("i1", "i2", "i3");
        NewOrderDto orderDto = new NewOrderDto(itemNames);

        Item item1 = new Item("i1", new BigDecimal("10.00"), false);
        Item item2 = new Item("i2", new BigDecimal("15.00"), true);
        Item item3 = new Item("i3", new BigDecimal("20.00"), false);
        when(itemRepository.findByName("i1")).thenReturn(Optional.of(item1));
        when(itemRepository.findByName("i2")).thenReturn(Optional.of(item2));
        when(itemRepository.findByName("i3")).thenReturn(Optional.of(item3));

        BigDecimal expectedTotal = new BigDecimal("35.00");
        when(tillFacade.calcTotal(anyList())).thenReturn(expectedTotal);

        ItemDto itemDto1 = new ItemDto(1L, "i1", new BigDecimal("10.00"), false);
        ItemDto itemDto2 = new ItemDto(2L, "i2", new BigDecimal("15.00"), true);
        ItemDto itemDto3 = new ItemDto(3L, "i3", new BigDecimal("20.00"), false);
        when(itemMapper.toDto(item1)).thenReturn(itemDto1);
        when(itemMapper.toDto(item2)).thenReturn(itemDto2);
        when(itemMapper.toDto(item3)).thenReturn(itemDto3);

        OrderDto result = service.tillCheckout(orderDto);

        assertEquals(itemNames.size(), result.getItems().size());
        assertEquals(expectedTotal + "aws", result.getTotalAmount());
        verify(itemRepository, times(itemNames.size())).findByName(anyString());
        verify(tillFacade, times(1)).calcTotal(anyList());
        verify(itemMapper, times(itemNames.size())).toDto(any(Item.class));
    }
}
package com.yostoya.shoptill.service.impl;

import com.yostoya.shoptill.domain.Item;
import com.yostoya.shoptill.domain.dto.OrderDto;
import com.yostoya.shoptill.domain.User;
import com.yostoya.shoptill.domain.dto.NewOrderDto;
import com.yostoya.shoptill.domain.dto.RegisterDto;
import com.yostoya.shoptill.domain.dto.UserDto;
import com.yostoya.shoptill.exception.notfound.UserNotFoundException;
import com.yostoya.shoptill.mapper.ItemMapper;
import com.yostoya.shoptill.mapper.UserMapper;
import com.yostoya.shoptill.repository.ItemRepository;
import com.yostoya.shoptill.repository.UserRepository;
import com.yostoya.shoptill.service.facade.TillFacade;
import com.yostoya.shoptill.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
    private final TillFacade tillFacade;

    @Override
    public UserDto register(final RegisterDto registerDto) {
        log.info("User registering..");
        return userMapper.toDto(userRepository.save(userMapper.toUser(registerDto)));
    }

    @Override
    public UserDto getUserByEmail(String email) {
        return userMapper.toDto(userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("email"
                , email)));
    }

    @Override
    public OrderDto tillCheckout(NewOrderDto orderDto) {

        final List<Item> items = orderDto.items().stream().map(s -> {
            final Item item = itemRepository.findByName(s).get();
            return new Item(item.getName(), item.getPrice(), item.isHalfPrice());
        }).toList();

        final BigDecimal total = tillFacade.calcTotal(items);
        String aws = total.compareTo(new BigDecimal("1.00")) > 0 ? "aws" : "c";

        return new OrderDto(
                UUID.randomUUID().toString(),
                1L,
                items.stream().map(itemMapper::toDto).toList(),
                total + aws);
    }

}

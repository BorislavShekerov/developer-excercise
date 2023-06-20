package com.yostoya.shoptill.service;

import com.yostoya.shoptill.domain.dto.OrderDto;
import com.yostoya.shoptill.domain.User;
import com.yostoya.shoptill.domain.dto.NewOrderDto;
import com.yostoya.shoptill.domain.dto.RegisterDto;
import com.yostoya.shoptill.domain.dto.UserDto;

public interface UserService {

    UserDto register( final RegisterDto registerDto);

    UserDto getUserByEmail(String email);

    OrderDto tillCheckout(final NewOrderDto orderDto);
}

package com.yostoya.shoptill.service;

import com.yostoya.shoptill.domain.dto.ItemDto;

public interface AdminService {

    ItemDto addItem(final ItemDto newItem);

    ItemDto update(final Long id, final ItemDto updateItem);

    void delete(Long id);
}

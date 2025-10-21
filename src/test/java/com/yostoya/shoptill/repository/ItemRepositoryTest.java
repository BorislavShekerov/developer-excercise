package com.yostoya.shoptill.repository;

import com.yostoya.shoptill.domain.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Test
    void findByName() {
        final Item item = new Item("i", BigDecimal.TEN, true);
        final Item expected = new Item("i", BigDecimal.TEN, true);
        ReflectionTestUtils.setField(expected, "id", 1L);

        itemRepository.save(item);
        final Item actual = itemRepository.findByName("i").get();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void findByName_isEmpty() {
        final Optional<Item> actual = itemRepository.findByName("i");
        assertThat(actual).isEmpty();
    }

}
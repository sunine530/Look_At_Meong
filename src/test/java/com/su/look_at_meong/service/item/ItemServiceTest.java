package com.su.look_at_meong.service.item;

import static com.su.look_at_meong.model.item.constatnt.Category.BAG;
import static com.su.look_at_meong.model.item.constatnt.Category.CLOTHES;
import static com.su.look_at_meong.model.item.constatnt.ItemStatus.SELL;
import static com.su.look_at_meong.model.item.constatnt.ItemStatus.SOLD_OUT;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.su.look_at_meong.model.item.constatnt.Category;
import com.su.look_at_meong.model.item.constatnt.ItemStatus;
import com.su.look_at_meong.model.item.dto.ItemDto;
import com.su.look_at_meong.model.item.entity.Item;
import com.su.look_at_meong.repository.ItemRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @InjectMocks
    private ItemService itemService;

    @Mock
    private ItemRepository itemRepository;

    @DisplayName("상풍 등록 - 성공")
    @Test
    void ITEM_ADD_SUCCESS() {
        //given
        given(itemRepository.save(any()))
            .willReturn(Item.builder()
                .itemName("test1")
                .price(10000)
                .stockNum(10)
                .itemStatus(SOLD_OUT)
                .category(CLOTHES)
                .build());

        //when
        ItemDto itemDto = itemService.addItem(Item.builder()
            .itemName("test2")
            .price(20000)
            .stockNum(20)
            .itemStatus(SELL)
            .category(BAG)
            .build());
        ArgumentCaptor<Item> captor = ArgumentCaptor.forClass(Item.class);

        //verify
        verify(itemRepository, times(1)).save(captor.capture());

        //then
        assertEquals("test1", itemDto.getItemName());
        assertEquals(10000, itemDto.getPrice());
        assertEquals(10, itemDto.getStockNum());
        assertEquals(SOLD_OUT, itemDto.getItemStatus());
        assertEquals(CLOTHES,itemDto.getCategory());

        assertEquals("test2", captor.getValue().getItemName());
        assertEquals(20000,captor.getValue().getPrice());
        assertEquals(20,captor.getValue().getStockNum());
        assertEquals(SELL, captor.getValue().getItemStatus());
        assertEquals(BAG, captor.getValue().getCategory());
    }

   //TODO 상품 등록 실패
}
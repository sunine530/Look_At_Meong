package com.su.look_at_meong.controller.Item;

import static com.su.look_at_meong.model.item.constatnt.Category.BAG;
import static com.su.look_at_meong.model.item.constatnt.ItemStatus.SELL;
import static com.su.look_at_meong.model.item.constatnt.ItemStatus.SOLD_OUT;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import com.su.look_at_meong.model.item.dto.ItemDto;
import com.su.look_at_meong.model.item.entity.Item;
import com.su.look_at_meong.service.item.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class ItemControllerTest {

    @InjectMocks
    private ItemController itemController;

    @Mock
    private ItemService itemService;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(itemController).build();
    }

    @DisplayName("상품 등록")
    @Test
    void ITEM_ADD_SUCCESS() throws Exception {
        //given
        ItemDto itemDto = itemDto();
        Item item = item();

        given(itemService.addItem(any())).willReturn(itemDto);

        //when
        ResultActions resultActions = mockMvc.perform(
            MockMvcRequestBuilders.post("/item/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(item))
        );

        //then
        MvcResult mvcResult = resultActions.andExpect(status().isOk())
            .andExpect(jsonPath("$.data.itemStatus").value(SELL.name()))
            .andExpect(jsonPath("itemName", itemDto.getItemName()).exists())
            .andReturn();
    }

    private ItemDto itemDto() {
        return ItemDto.builder()
            .itemName("test2")
            .price(20000)
            .stockNum(20)
            .itemStatus(SELL)
            .category(BAG)
            .build();
    }

    private Item item() {
        return Item.builder()
            .itemName("test1")
            .price(20000)
            .stockNum(20)
            .itemStatus(SOLD_OUT)
            .category(BAG)
            .build();
    }
}
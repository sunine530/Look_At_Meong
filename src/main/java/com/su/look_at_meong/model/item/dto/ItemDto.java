package com.su.look_at_meong.model.item.dto;

import com.su.look_at_meong.model.item.constatnt.Category;
import com.su.look_at_meong.model.item.constatnt.ItemStatus;
import com.su.look_at_meong.model.item.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemDto {

    private String itemName;
    private int price;
    private String detail;
    private int stockNum;
    private Category category;
    private ItemStatus itemStatus;

    public ItemDto toEntity(Item item) {

        ItemDto itemDto = new ItemDto();
        itemDto.setItemName(item.getItemName());
        itemDto.setPrice(item.getPrice());
        itemDto.setDetail(item.getDetail());
        itemDto.setStockNum(item.getStockNum());
        itemDto.setCategory(item.getCategory());
        itemDto.setItemStatus(item.getItemStatus());
        return itemDto;
    }
}

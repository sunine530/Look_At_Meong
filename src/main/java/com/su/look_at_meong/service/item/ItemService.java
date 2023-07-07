package com.su.look_at_meong.service.item;

import static com.su.look_at_meong.exception.constant.ItemErrorCode.ITEM_SOLD_OUT;
import static com.su.look_at_meong.model.item.constatnt.ItemStatus.SELL;
import static com.su.look_at_meong.model.item.constatnt.ItemStatus.SOLD_OUT;

import com.su.look_at_meong.exception.RestApiException;
import com.su.look_at_meong.model.item.dto.ItemDto;
import com.su.look_at_meong.model.item.entity.Item;
import com.su.look_at_meong.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemDto addItem(Item item) {

        if(item.getItemStatus().equals(SOLD_OUT)){
            throw new RestApiException(ITEM_SOLD_OUT);
        }

        item.setItemStatus(SELL);
        return new ItemDto().toEntity(itemRepository.save(item));
    }

    public ItemDto updateItem(Item updateItem, Long memberId, Long itemId) {

        var item = itemRepository.findByMemberIdAndItemId(memberId, itemId)
            .orElseThrow(() -> new RuntimeException());

        if (updateItem.getItemStatus().equals(SOLD_OUT)) {
            throw new RestApiException(ITEM_SOLD_OUT);
        }


        item.setItemName(updateItem.getItemName());
        item.setItemStatus(updateItem.getItemStatus());
        item.setPrice(updateItem.getPrice());
        item.setDetail(updateItem.getDetail());
        item.setStockNum(updateItem.getStockNum());
        item.setCategory(updateItem.getCategory());

        return new ItemDto().toEntity(itemRepository.save(item));
    }
}

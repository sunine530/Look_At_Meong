package com.su.look_at_meong.service.item;

import static com.su.look_at_meong.model.item.constatnt.ItemStatus.SELL;
import static com.su.look_at_meong.model.item.constatnt.ItemStatus.SOLD_OUT;

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
            //TODO 예외처리
            throw new RuntimeException("품절입니다.");
        }

        item.setItemStatus(SELL);
        return new ItemDto().toDto(itemRepository.save(item));
    }
}

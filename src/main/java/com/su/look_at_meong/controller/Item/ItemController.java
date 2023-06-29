package com.su.look_at_meong.controller.Item;

import com.su.look_at_meong.config.jwt.JwtProvider;
import com.su.look_at_meong.model.item.dto.ItemDto;
import com.su.look_at_meong.model.item.entity.Item;
import com.su.look_at_meong.service.item.ItemService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;

    @PostMapping("/add")
    ResponseEntity<ItemDto> addItem(@RequestBody @Valid Item item, BindingResult bindingResult) {

        if (bindingResult.hasErrors()){
            throw new RuntimeException("잘못된 인풋입니다.");
        }

        return ResponseEntity.ok(itemService.addItem(item));
    }
}

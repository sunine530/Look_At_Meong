package com.su.look_at_meong.model.item.constatnt;

public enum Category {

    CLOTHES("의류"),
    BAG("가방"),
    TUMBLER("텀블러"),
    PHONE_CASE("폰케이스"),
    ETC("기타")
    ;

    final String type;

    Category(String type) {
        this.type = type;
    }
}

package com.su.look_at_meong.model.item.constatnt;

public enum ItemStatus {

    SELL("판매중"),
    SOLD_OUT("품절")
    ;

    private final String status;

    ItemStatus(String status) {
        this.status = status;
    }
}

package com.ll.exam.springbatch.app.product.entity;

import com.ll.exam.springbatch.app.base.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class ProductOption extends BaseEntity {
    private String color;
    private String size;
    private String displayColor;
    private String displaySize;
    private int price;
    private int salePrice;
    private int wholesalePrice;
    @ManyToOne(fetch = LAZY)
    @ToString.Exclude
    private Product product;
    private boolean isSoldOut; // 사입처에서의 품절여부
    private int stockQuantity; // 쇼핑몰에서 보유한 물건 개수

    public ProductOption(String color, String size) {
        this.color = color;
        this.displayColor = color;
        this.size = size;
        this.displaySize = size;
    }

    public boolean isOrderable(int quantity) {
        if (isSoldOut() == false) return true;

        return getStockQuantity() >= quantity;
    }
}
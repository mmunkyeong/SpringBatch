package com.ll.exam.springbatch.app.order.entity;

import com.ll.exam.springbatch.app.base.entity.BaseEntity;
import com.ll.exam.springbatch.app.product.entity.ProductOption;
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
public class OrderItem extends BaseEntity {
    @ManyToOne(fetch = LAZY)
    @ToString.Exclude
    private Order order;

    @ManyToOne(fetch = LAZY)
    private ProductOption productOption;

    private int quantity;
    // 가격
    private int price; // 권장판매가
    private int salePrice; // 실제판매가
    private int wholesalePrice; // 도매가

    public OrderItem(ProductOption productOption, int quantity) {
        this.productOption = productOption;
        this.quantity = quantity;
        this.price = productOption.getPrice();
        this.salePrice = productOption.getSalePrice();
        this.wholesalePrice = productOption.getWholesalePrice();
    }
}

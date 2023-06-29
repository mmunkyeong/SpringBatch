package com.ll.exam.springbatch.app.cart.entity;


import com.ll.exam.springbatch.app.base.entity.BaseEntity;
import com.ll.exam.springbatch.app.member.entity.Member;
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
public class CartItem extends BaseEntity {
    @ManyToOne(fetch = LAZY)
    private Member member;
    @ManyToOne(fetch = LAZY)
    private ProductOption productOption;
    private int quantity; // 쇼핑몰에서 보유한 물건 개수
}


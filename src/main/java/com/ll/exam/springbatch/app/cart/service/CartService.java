package com.ll.exam.springbatch.app.cart.service;


import com.ll.exam.springbatch.app.cart.entity.CartItem;
import com.ll.exam.springbatch.app.cart.repository.CartItemRepository;
import com.ll.exam.springbatch.app.member.entity.Member;
import com.ll.exam.springbatch.app.product.entity.ProductOption;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartItemRepository cartItemRepository;

    public void addItem(Member member, ProductOption productOption, int quantity) {
        CartItem cartItem = CartItem.builder()
                .member(member)
                .productOption(productOption)
                .quantity(quantity)
                .build();

        cartItemRepository.save(cartItem);
    }
}

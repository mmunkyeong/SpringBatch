package com.ll.exam.springbatch.app.product.service;

import com.ll.exam.springbatch.app.product.entity.Product;
import com.ll.exam.springbatch.app.product.entity.ProductOption;
import com.ll.exam.springbatch.app.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product create(String name, int salePrice, int wholesalePrice,  String makerShopName, List<ProductOption> options) {
        int price=(int)Math.ceil(wholesalePrice*1.6)/100*100;
        Product product = Product.builder()
                .name(name)
                .salePrice(salePrice)
                .price(price)
                .wholesalePrice(wholesalePrice)
                .makerShopName(makerShopName)
                .build();

        for ( ProductOption option : options ) {
            product.addOption(option);
        }
        productRepository.save(product);
        return product;
    }
}
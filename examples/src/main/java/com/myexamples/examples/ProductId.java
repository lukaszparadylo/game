package com.myexamples.examples;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@RequiredArgsConstructor
public class ProductId {

    private Long productId;
    private Double qty;

    @Bean
    public Long getProductId() {
        return 10L;
    }
    @Bean
    public Double getQty() {
        return 20.0;
    }
}

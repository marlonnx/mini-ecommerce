package com.example.ecommerce.config;

import com.example.ecommerce.domain.cart.Cart;
import com.example.ecommerce.domain.cart.dto.CartDto;
import com.example.ecommerce.domain.cart.dto.CartItemDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        mapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
//                .setSkipNullEnabled(true)
                .setFieldMatchingEnabled(true);

        // Custom mappings
        configureCartMapping(mapper);

        return mapper;
    }

    private void configureCartMapping(ModelMapper mapper) {
        mapper.createTypeMap(Cart.class, CartDto.class)
                .setPostConverter(context -> {
                            Cart src = context.getSource();

                            return new CartDto(
                                    src.getId(),
                                    src.getItems()
                                            .stream()
                                            .map(item -> mapper.map(item, CartItemDto.class))
                                            .toList()
                            );
                        }
                );
    }
}

package com.example.ecommerce.domain.product;

import com.example.ecommerce.domain.product.dto.CreateProductDto;
import com.example.ecommerce.domain.product.dto.ProductDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    final ProductRepository productRepository;
    final ModelMapper mapper;


    public Page<ProductDto> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(p -> mapper.map(p, ProductDto.class));
    }

    public ProductDto createProduct(CreateProductDto productDto) {
        final var product = productRepository.save(
                Product.builder()
                        .name(productDto.name())
                        .description(productDto.description())
                        .quantity(productDto.quantity())
                        .images(
                                List.of(
                                        "https://picsum.photos/1280/720",
                                        "https://picsum.photos/1280/720",
                                        "https://picsum.photos/1280/720"
                                )
                        )
                        .build()
        );
        return mapper.map(product, ProductDto.class);
    }

    public ProductDto getProductById(Long id) {
        final var product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        return mapper.map(product, ProductDto.class);
    }

    public ProductDto updateProduct(Long id, ProductDto productDto) {
        final var product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        product.setName(productDto.name());
        product.setDescription(productDto.description());
        product.setQuantity(productDto.quantity());
        return mapper.map(productRepository.save(product), ProductDto.class);
    }

    public void deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Product not found");
        }
    }
}

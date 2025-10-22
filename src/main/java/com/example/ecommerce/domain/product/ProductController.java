package com.example.ecommerce.domain.product;

import com.example.ecommerce.domain.product.dto.CreateProductDto;
import com.example.ecommerce.domain.product.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/products")
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }


    @GetMapping
    public Page<ProductDto> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return service.findAll(page, size);
    }

    @PostMapping
    public ProductDto save(@RequestBody CreateProductDto productDto) {
        return service.createProduct(productDto);
    }

    @PutMapping(path = "/{id}")
    public ProductDto update(@RequestBody ProductDto productDto, @PathVariable Long id) {
        return service.updateProduct(id, productDto);
    }

    @GetMapping(path = "/{id}")
    public ProductDto findById(@PathVariable Long id) {
        return service.getProductById(id);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteById(@PathVariable Long id) {
        service.deleteProduct(id);
    }

}

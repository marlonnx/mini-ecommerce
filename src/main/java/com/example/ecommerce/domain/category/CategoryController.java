package com.example.ecommerce.domain.category;

import com.example.ecommerce.domain.category.model.CategoryDto;
import com.example.ecommerce.domain.product.dto.ProductDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryRepository) {
        this.categoryService = categoryRepository;
    }

    @GetMapping
    public List<CategoryDto> findAll() {
        return categoryService.findAll();
    }

    @PostMapping
    public CategoryDto save(@RequestBody CategoryDto category) {
        return categoryService.save(category);
    }

    @GetMapping(path = "/{id}")
    public CategoryDto getCategory(@PathVariable(name = "id") Long id) {
        return categoryService.findById(id);
    }

    @PutMapping(path = "/{id}")
    public CategoryDto update(@RequestBody CategoryDto category, @PathVariable(name = "id") Long id) {
        return categoryService.update(category, id);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteCategory(@PathVariable(name = "id") Long id) {
        categoryService.delete(id);
    }

    @GetMapping(path = "/{id}/products")
    public List<ProductDto> getProducts(@PathVariable(name = "id") Long id) {
        return categoryService.findCategoryProducts(id);
    }

}

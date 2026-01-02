package com.example.ecommerce.domain.category;

import com.example.ecommerce.domain.category.model.CategoryDto;
import com.example.ecommerce.domain.product.dto.ProductDto;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryService(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    public List<CategoryDto> findAll() {
        return categoryRepository.findAll().stream().map((element) -> modelMapper.map(element, CategoryDto.class)).toList();
    }

    public CategoryDto findById(long id) throws EntityNotFoundException {
        return categoryRepository.findById(id)
                .map((element) -> modelMapper.map(element, CategoryDto.class))
                .orElseThrow(() -> new EntityNotFoundException("Category not found for id: " + id));
    }

    public List<ProductDto> findCategoryProducts(Long id) throws EntityNotFoundException {
        final Category category = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Category not found for id: " + id));
        return category.getProducts().stream().map((element) -> modelMapper.map(element, ProductDto.class)).toList();
    }

    public CategoryDto save(CategoryDto dto) {
        final Category category = Category.builder().name(dto.name()).image("https://picsum.photos/1280/720").build();
        return modelMapper.map(categoryRepository.save(category), CategoryDto.class);
    }

    public CategoryDto update(CategoryDto dto, Long id) throws EntityNotFoundException {
        final var category = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Category not found for id: " + id));
        category.setName(dto.name());
        category.setImage(dto.image());
        return modelMapper.map(categoryRepository.save(category), CategoryDto.class);
    }

    public void delete(long id) {
        final Category category = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Category not found for id: " + id));
        categoryRepository.delete(category);
    }


}

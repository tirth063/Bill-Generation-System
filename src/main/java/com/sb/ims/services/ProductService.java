package com.sb.ims.services;

import com.sb.ims.model.Product;
import com.sb.ims.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductService {

    private final ProductRepository productRepository;
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(long id) {
        return productRepository.findById(id).orElse(new Product());
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public List<Product> saveProducts(List<Product> products) {
        return productRepository.saveAll(products);
    }

    public void deleteById(long id) {
        productRepository.deleteById(id);
    }

    public void delete() {
        productRepository.deleteAll();
    }
}

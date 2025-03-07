package com.sb.ims.controller;

import com.sb.ims.model.Product;
import com.sb.ims.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        return new ResponseEntity<>(productService.findAll() , HttpStatus.OK );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable int id) {
        return new ResponseEntity<>(productService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<?> saveAllProducts(@RequestBody List<Product> products) {
        return new ResponseEntity<>(productService.saveProducts(products) , HttpStatus.OK );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable int id) {
        productService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/")
    public ResponseEntity<?> deleteAllProducts() {
        productService.delete();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

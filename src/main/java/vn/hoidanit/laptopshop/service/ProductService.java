package vn.hoidanit.laptopshop.service;

import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.ProductRepository;

import java.util.List;

import org.springframework.stereotype.Service;

@Service

public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> fetchProducts() {
        return this.productRepository.findAll();
    }

    public Product getProductsById(long id) {
        return this.productRepository.findById(id);
    }

    public void deleteProduct(long id) {
        this.productRepository.deleteById(id);
    }

    public Product createProduct(Product product) {
        return this.productRepository.save(product);
    }

}

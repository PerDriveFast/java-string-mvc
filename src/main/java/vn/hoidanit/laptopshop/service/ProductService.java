package vn.hoidanit.laptopshop.service;

import vn.hoidanit.laptopshop.domain.Cart;
import vn.hoidanit.laptopshop.domain.CartDetail;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.CartDetailRepository;
import vn.hoidanit.laptopshop.repository.CartRepository;
import vn.hoidanit.laptopshop.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.stereotype.Service;

@Service

public class ProductService {

    private final DaoAuthenticationProvider authProvider;

    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final UserService userService;

    public ProductService(ProductRepository productRepository,
            CartRepository cartRepository,
            CartDetailRepository cartDetailRepository,
            UserService userService, DaoAuthenticationProvider authProvider) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.userService = userService;
        this.authProvider = authProvider;
    }

    public List<Product> fetchProducts() {
        return this.productRepository.findAll();
    }

    public Optional<Product> getProductsById(long id) {
        return this.productRepository.findById(id);
    }

    public void deleteProduct(long id) {
        this.productRepository.deleteById(id);
    }

    public Product createProduct(Product product) {
        return this.productRepository.save(product);
    }

    public void handleAddProductToCart(String email, long productId) {
        // check user đã có cart chưa ? nếu chưa -> tạo mới
        User user = this.userService.getUserByEmail(email);

        if (user != null) {

            Cart cart = this.cartRepository.findByUser(user);

            if (cart == null) {
                Cart otherCart = new Cart();
                otherCart.setUser(user);
                otherCart.setSum(1);

                this.cartRepository.save(otherCart);
            }

            // search for product id

            Optional<Product> producOptional = this.productRepository.findById(productId);
            if (producOptional.isPresent()) {
                Product realProduct = producOptional.get();

                CartDetail cartDetail = new CartDetail();

                cartDetail.setCart(cart);
                cartDetail.setProduct(realProduct);
                cartDetail.setPrice(realProduct.getPrice());
                cartDetail.setQuantity(1);

                this.cartDetailRepository.save(cartDetail);
                // save user
            }

        }
    }
}

package vn.hoidanit.laptopshop.service;

import vn.hoidanit.laptopshop.controller.admin.DashboardController;
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
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service

public class ProductService {

    private final SecurityFilterChain filterChain;

    private final DashboardController dashboardController;

    private final CustomUserDetailsService customUserDetailsService;

    private final AuthenticationSuccessHandler customSuccessHandler;

    private final DaoAuthenticationProvider authProvider;

    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final UserService userService;

    public ProductService(ProductRepository productRepository,
            CartRepository cartRepository,
            CartDetailRepository cartDetailRepository,
            UserService userService, DaoAuthenticationProvider authProvider,
            AuthenticationSuccessHandler customSuccessHandler, CustomUserDetailsService customUserDetailsService,
            DashboardController dashboardController, SecurityFilterChain filterChain) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.userService = userService;
        this.authProvider = authProvider;
        this.customSuccessHandler = customSuccessHandler;
        this.customUserDetailsService = customUserDetailsService;
        this.dashboardController = dashboardController;
        this.filterChain = filterChain;
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

    public Cart fetchByUser(User user) {
        return this.cartRepository.findByUser(user);
    }

    public void handleRemoveCartDetail(long cartDetailId, HttpSession session) {
        Optional<CartDetail> cartDetailOptional = this.cartDetailRepository.findById(cartDetailId);

        if (cartDetailOptional.isPresent()) {
            CartDetail cartDetail = cartDetailOptional.get();
            Cart cart = cartDetail.getCart();

            // Kiểm tra nếu cart null
            if (cart == null) {
                throw new RuntimeException("Cart is null for CartDetail ID: " + cartDetailId);
            }

            // Xóa cartDetail
            this.cartDetailRepository.deleteById(cartDetailId);

            int cartSum = cart.getSum();
            if (cartSum > 1) {
                cart.setSum(cartSum - 1);
                session.setAttribute("sum", cart.getSum());
                this.cartRepository.save(cart);
            } else {
                this.cartRepository.deleteById(cart.getId());
                session.setAttribute("sum", 0);
            }
        }
    }

    public void handleAddProductToCart(String email, long productId, HttpSession session) {
        // check user đã có cart chưa ? nếu chưa -> tạo mới
        User user = this.userService.getUserByEmail(email);

        if (user != null) {

            Cart cart = this.cartRepository.findByUser(user);

            if (cart == null) {
                Cart otherCart = new Cart();
                otherCart.setUser(user);
                otherCart.setSum(0);

                this.cartRepository.save(otherCart);
            }

            // search for product id

            Optional<Product> producOptional = this.productRepository.findById(productId);
            if (producOptional.isPresent()) {
                Product realProduct = producOptional.get();

                // chec san pham da tung them vao gio hang truoc day hay chua

                CartDetail oldDetail = this.cartDetailRepository.findByCartAndProduct(cart, realProduct); //

                if (oldDetail == null) {
                    CartDetail cartDetail = new CartDetail();

                    cartDetail.setCart(cart);
                    cartDetail.setProduct(realProduct);
                    cartDetail.setPrice(realProduct.getPrice());
                    cartDetail.setQuantity(1);

                    this.cartDetailRepository.save(cartDetail);

                    // update cart (sum)
                    int s = cart.getSum() + 1;
                    cart.setSum(cart.getSum() + 1);
                    this.cartRepository.save(cart);
                    session.setAttribute("sum", s);
                } else {
                    oldDetail.setQuantity(oldDetail.getQuantity() + 1);
                    this.cartDetailRepository.save(oldDetail);
                }

                //

                // save user
            }

        }
    }

    public void handleUpdateCartBeforeCheckout(List<CartDetail> cartDetails) {
        for (CartDetail cartDetail : cartDetails) {
            Optional<CartDetail> cdOptional = this.cartDetailRepository.findById(cartDetail.getId());
            if (cdOptional.isPresent()) {
                CartDetail currentDetail = cdOptional.get();
                currentDetail.setQuantity(cartDetail.getQuantity());
                this.cartDetailRepository.save(currentDetail);
            }
        }
    }
}

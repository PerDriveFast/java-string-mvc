package vn.hoidanit.laptopshop.service;

import vn.hoidanit.laptopshop.controller.admin.DashboardController;
import vn.hoidanit.laptopshop.domain.Cart;
import vn.hoidanit.laptopshop.domain.CartDetail;
import vn.hoidanit.laptopshop.domain.Order;
import vn.hoidanit.laptopshop.domain.OrderDetail;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.Product_;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.CartDetailRepository;
import vn.hoidanit.laptopshop.repository.CartRepository;
import vn.hoidanit.laptopshop.repository.OrderDetailRepository;
import vn.hoidanit.laptopshop.repository.OrderRepository;
import vn.hoidanit.laptopshop.repository.ProductRepository;
import vn.hoidanit.laptopshop.service.Specification.ProductSpecs;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    private final UserService userService;
    private final OrderService orderService;

    public ProductService(ProductRepository productRepository,
            CartRepository cartRepository,
            CartDetailRepository cartDetailRepository,
            UserService userService, DaoAuthenticationProvider authProvider,
            AuthenticationSuccessHandler customSuccessHandler, CustomUserDetailsService customUserDetailsService,
            DashboardController dashboardController, SecurityFilterChain filterChain,
            OrderService orderService, OrderRepository orderRepository,
            OrderDetailRepository orderDetailRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.userService = userService;
        this.authProvider = authProvider;
        this.customSuccessHandler = customSuccessHandler;
        this.customUserDetailsService = customUserDetailsService;
        this.dashboardController = dashboardController;
        this.filterChain = filterChain;
        this.orderService = orderService;
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    public Page<Product> getAllProduct(Pageable page) {
        return this.productRepository.findAll(page);
    }

    public Page<Product> fetchProducts(Pageable page) {
        return this.productRepository.findAll(page);
    }

    // public Page<Product> fetchProductsWithSpec(Pageable page, String name) {
    // return this.productRepository.findAll(ProductSpecs.nameLike(name), page);
    // }

    public Page<Product> fetchProductsWithSpec(Pageable page, double min) {
        return this.productRepository.findAll(ProductSpecs.minPrice(min), page);
    }

    public Optional<Product> getProductById(Long id) {
        return this.productRepository.findById(id);
    }

    public void deleteProduct(Long id) {
        this.productRepository.deleteById(id);
    }

    public Product createProduct(Product product) {
        return this.productRepository.save(product);
    }

    public Cart fetchByUser(User user) {
        return this.cartRepository.findByUser(user);
    }

    public Cart getCartByUser(User user) {
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

    public void handleAddProductToCart(String email, long productId, HttpSession session, long quantity) {

        User user = this.userService.getUserByEmail(email);
        // Check has user had cart? Not yet -> create new one
        if (user != null) {
            Cart cart = this.cartRepository.findByUser(user);
            if (cart == null) {
                Cart newCart = new Cart();
                newCart.setUser(user);
                newCart.setSum(0);

                cart = this.cartRepository.save(newCart);
            }

            // find product
            Optional<Product> productOptional = this.productRepository.findById(productId);
            if (productOptional.isPresent()) {
                Product realProduct = productOptional.get();
                // Check has product existed in cart?
                CartDetail oldDetail = this.cartDetailRepository.findByCartAndProduct(cart, realProduct);

                if (oldDetail == null) {
                    // Save cart detail
                    CartDetail cartDetail = new CartDetail();
                    cartDetail.setCart(cart);
                    cartDetail.setProduct(realProduct);
                    cartDetail.setPrice(realProduct.getPrice());
                    cartDetail.setQuantity(quantity);
                    this.cartDetailRepository.save(cartDetail);

                    int sumCart = cart.getSum() + 1;
                    cart.setSum(sumCart);

                    this.cartRepository.save(cart);
                    session.setAttribute("sum", sumCart);
                } else {
                    oldDetail.setQuantity(oldDetail.getQuantity() + quantity);
                    this.cartDetailRepository.save(oldDetail);
                }

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

    public void handlePlaceOrder(User user, HttpSession session,
            String receiverName,
            String receiverAddress,
            String receiverPhone) {

        // Create Order detail
        // Step 1
        Cart cart = this.cartRepository.findByUser(user);
        if (cart != null) {
            List<CartDetail> cartDetails = cart.getCartDetails();
            if (cartDetails != null) {

                // Create Order
                Order order = new Order();
                order.setUser(user);
                order.setReceiverName(receiverName);
                order.setReceiverAddress(receiverAddress);
                order.setReceiverPhone(receiverPhone);
                order.setStatus("PENDING");

                double sum = 0;
                for (CartDetail cd : cartDetails) {
                    sum += cd.getPrice();
                }
                order.setTotalPrice(sum);
                order = this.orderRepository.save(order);

                for (CartDetail cd : cartDetails) {
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setOrder(order);
                    orderDetail.setProduct(cd.getProduct());
                    orderDetail.setQuantity(cd.getQuantity());
                    orderDetail.setPrice(cd.getPrice());
                    this.orderDetailRepository.save(orderDetail);
                }
            }
            // Step 2 delete cartDetail and cart
            for (CartDetail cd : cartDetails) {
                this.cartDetailRepository.deleteById(cd.getId());
            }
            this.cartRepository.deleteById(cart.getId());

            // Step 3 Update session
            // Thanh toan het cart nen set cart.sum = 0
            session.setAttribute("sum", 0);
        }
    }

}

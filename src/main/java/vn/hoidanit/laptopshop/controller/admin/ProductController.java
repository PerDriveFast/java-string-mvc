package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.service.UploadService;
import vn.hoidanit.laptopshop.service.ProductService;

@Controller
public class ProductController {

    private final ProductService productService;
    private final UploadService uploadService;

    // source action -> generate contractor

    public ProductController(ProductService productService, UploadService uploadService) {
        this.productService = productService;
        this.uploadService = uploadService;
        // this.ProductRepository = ProductRepository;
    }

    @GetMapping("/admin/product")
    public String getProduct(Model model) {
        List<Product> prs = this.productService.fetchProducts();
        model.addAttribute("products", prs);
        return "/admin/product/show";
    }

    @GetMapping("/admin/product/create")
    public String get(Model model) {
        model.addAttribute("newProduct", new Product());
        return "admin/product/create";
    }

    @PostMapping("/admin/product/create")
    public String CreateProductPage(Model model, @ModelAttribute("newProduct") @Valid Product product,
            BindingResult newProductBindingResult,
            @RequestParam("hoidanitFile") MultipartFile file) {

        // validate
        if (newProductBindingResult.hasErrors()) {
            return "/admin/product/create";
        }
        //
        String image = this.uploadService.handleSaveUploadFile(file, "product");

        product.setImage(image);
        // save
        this.productService.createProduct(product);

        return "redirect:/admin/product";
    }

    @RequestMapping("/admin/product/{id}")
    public String getProductDetailPage(Model model, @PathVariable long id) {
        Product product = this.productService.getProductsById(id);
        model.addAttribute("product", product);
        model.addAttribute("id", id);
        return "admin/product/detail";
    }

    @GetMapping("/admin/product/delete/{id}")
    public String getDeleteProduct(Model model, @PathVariable long id) {
        model.addAttribute("id", id);
        // Product Product = new Product();
        // Product.setId(id);
        model.addAttribute("newProduct", new Product());
        return "admin/product/delete";
    }

    @PostMapping("/admin/product/delete")
    public String postDeleteProduct(@ModelAttribute("newProduct") Product product) {
        this.productService.deleteProduct(product.getId());
        return "redirect:/admin/product"; // Đúng đường dẫn sau khi xóa
    }

    @RequestMapping("/admin/product/update/{id}")
    public String getUpdateProductPage(Model model, @PathVariable long id) {
        Product currentProduct = this.productService.getProductsById(id);
        model.addAttribute("newProduct", currentProduct);
        return "admin/product/update";
    }

    @PostMapping("/admin/product/update")
    public String PostUpdateProduct(Model model,
            @ModelAttribute("newProduct") Product product,
            @RequestParam(value = "hoidanitFile", required = false) MultipartFile file) {

        Product currentProduct = this.productService.getProductsById(product.getId());

        if (currentProduct != null) {
            currentProduct.setDetailDesc(product.getDetailDesc());
            currentProduct.setShortDesc(product.getShortDesc());
            currentProduct.setFactory(product.getFactory());
            currentProduct.setPrice(product.getPrice());
            currentProduct.setQuantity(product.getQuantity());
            currentProduct.setSold(product.getSold());

            // Nếu có file mới, cập nhật avatar
            if (file != null && !file.isEmpty()) {
                String avatarPath = this.uploadService.handleSaveUploadFile(file, "avatar");
                currentProduct.setImage(avatarPath);
            }

            // Cập nhật Role

            this.productService.createProduct(currentProduct);
        }
        return "redirect:/admin/product";
    }
}

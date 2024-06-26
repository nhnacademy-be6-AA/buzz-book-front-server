package store.buzzbook.front.controller.product;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import store.buzzbook.front.client.coupon.CouponPolicyClient;
import store.buzzbook.front.client.product.ProductClient;
import store.buzzbook.front.client.user.UserClient;
import store.buzzbook.front.common.exception.product.ProductNotFoundException;
import store.buzzbook.front.dto.coupon.CouponPolicyResponse;
import store.buzzbook.front.dto.coupon.DownloadCouponRequest;
import store.buzzbook.front.dto.product.ProductRequest;
import store.buzzbook.front.dto.product.ProductResponse;

@Controller
@Slf4j
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

	private final ProductClient productClient;
	private final CouponPolicyClient couponPolicyClient;
	private final UserClient userClient;

	@GetMapping
	public String getAllProduct(Model model,
		@RequestParam(required = false, defaultValue = "0") int page,
		@RequestParam(required = false, defaultValue = "10") int size) {

		Page<ProductResponse> productPage = productClient.getAllProducts(page, size);
		List<ProductRequest> products = mapToProductRequest(productPage.getContent());

		model.addAttribute("products", products);
		model.addAttribute("productPage", productPage);
		model.addAttribute("page", "product");

		return "index";
	}

	@GetMapping("/{id}")
	public String getProductDetail(@PathVariable("id") int id, Model model) {
		ProductResponse response = fetchProductById(id);
		ProductRequest product = mapToProductRequest(response);
		List<CouponPolicyResponse> couponPolicies = couponPolicyClient.getSpecificCouponPolicies(id);

		model.addAttribute("product", product);
		model.addAttribute("title", "상품상세");
		model.addAttribute("couponPolicies", couponPolicies);
		return "pages/product/product-detail";
	}

	@PostMapping("/download-coupon")
	public ResponseEntity<Void> downloadSpecificCoupon(@RequestBody Map<String, Integer> request) {
		int couponPolicyId = request.get("couponPolicyId");

		try {
			userClient.downloadCoupon(DownloadCouponRequest.create(1L, couponPolicyId));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		return ResponseEntity.ok().build();
	}

	private ProductResponse fetchProductById(int id) {
		try {
			return productClient.getProductById(id);
		} catch (Exception e) {
			log.error("패치 에러 Product detail:", e);
			throw new ProductNotFoundException("상품 상세 정보 패치실패 ", e);
		}
	}

	private List<ProductRequest> mapToProductRequest(List<ProductResponse> responses) {
		return responses.stream()
			.map(this::mapToProductRequest)
			.toList();
	}

	private ProductRequest mapToProductRequest(ProductResponse productResponse) {
		return ProductRequest.builder()
			.id(productResponse.getId())
			.stock(productResponse.getStock())
			.price(productResponse.getPrice())
			.forwardDate(productResponse.getForwardDate())
			.score(productResponse.getScore())
			.thumbnailPath(productResponse.getThumbnailPath())
			.categoryId(productResponse.getCategory().getId())
			.productName(productResponse.getProductName())
			.stockStatus(productResponse.getStockStatus())
			.build();
	}
}

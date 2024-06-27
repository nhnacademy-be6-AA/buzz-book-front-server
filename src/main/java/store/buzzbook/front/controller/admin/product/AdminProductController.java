package store.buzzbook.front.controller.admin.product;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import store.buzzbook.front.client.product.ProductClient;
import store.buzzbook.front.common.exception.product.ProductNotFoundException;
import store.buzzbook.front.dto.product.ProductRequest;
import store.buzzbook.front.dto.product.ProductResponse;
import store.buzzbook.front.dto.product.ProductUpdateRequest;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/product")
public class AdminProductController {

	private final ProductClient productClient;

	@GetMapping
	public String adminPage(Model model,@RequestParam(required = false,defaultValue = "0") int page,
										@RequestParam(required = false,defaultValue = "10") int size) {
		Page<ProductResponse> productPage = productClient.getAllProducts(page,size);
		List<ProductRequest> products = mapToProductRequest(productPage.getContent());
		model.addAttribute("products", products);
		model.addAttribute("page", productPage);
		return "admin/pages/product-manage";
	}

	@GetMapping("/edit/{id}")
	private String editProduct(@PathVariable("id") int id, Model model)
	{
		ProductResponse response = fetchProductById(id);
		ProductRequest product = mapToProductRequest(response);
		model.addAttribute("product", product);

		return "admin/pages/product-manage-edit";
	}

	@PostMapping("/edit/{id}")
	public String editProduct(@PathVariable("id") int id, @ModelAttribute ProductUpdateRequest productUpdateRequest) {
		productClient.updateProduct(id,productUpdateRequest);

		return "redirect:/admin/product?page=1";
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
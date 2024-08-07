package store.buzzbook.front.client.product;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import store.buzzbook.front.dto.elasticsearch.BookDocumentResponse;
import store.buzzbook.front.dto.product.CategoryResponse;
import store.buzzbook.front.dto.product.ProductDetailResponse;
import store.buzzbook.front.dto.product.ProductRequest;
import store.buzzbook.front.dto.product.ProductResponse;
import store.buzzbook.front.dto.product.ProductUpdateRequest;

@FeignClient(name = "ProductClient", url = "http://${api.gateway.host}:${api.gateway.port}/api")
public interface ProductClient {

	@GetMapping("/products")
	Page<ProductResponse> getAllProducts(
		@RequestParam("status") String status,
		@RequestParam("name") String name,
		@RequestParam("elasticName") String elasticName,
		@RequestParam("categoryId") Integer categoryId,
		@RequestParam("orderBy") String orderBy,
		@RequestParam("pageNo") int pageNo,
		@RequestParam("pageSize") int pageSize);

	@PostMapping("/products")
	ProductRequest addProduct(@RequestBody ProductRequest productRequest);

	@GetMapping("/products/{id}")
	ProductResponse getProductById(@PathVariable("id") int id);

	@PutMapping("/products/{id}")
	ProductResponse updateProduct(@PathVariable("id") int id, @RequestBody ProductUpdateRequest productRequest);

	//엘라스틱 서치 대체 임시용
	@GetMapping("/products/search")
	List<ProductResponse> searchProducts(@RequestParam String title);

	@GetMapping("/products/categories")
	List<CategoryResponse> getAllCategories();

	// 상품 상세 정보 조회
	@GetMapping("/products/{id}/detail")
	ProductDetailResponse getProductDetail(@PathVariable("id") int id);

	// 최신 상품 조회
	@GetMapping("/products/latest")
	List<ProductResponse> getLatestProduct(@RequestParam("count") int count);

	// 엘라스틱 서치 API
	@GetMapping("/product-search/search")
	ResponseEntity<Page<BookDocumentResponse>> searchProducts(
		@RequestParam("query") String query,
		@RequestParam("pageNo") int pageNo,
		@RequestParam("pageSize") int pageSize
	);

	// MySQL의 데이터를 Elasticsearch로 변환
	@GetMapping("/product-search/datainit")
	ResponseEntity<Long> initDataTransfer();

}

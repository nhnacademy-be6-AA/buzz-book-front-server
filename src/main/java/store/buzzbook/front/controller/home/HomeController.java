package store.buzzbook.front.controller.home;

import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import store.buzzbook.front.client.coupon.CouponPolicyClient;
import store.buzzbook.front.client.product.ProductClient;
import store.buzzbook.front.dto.coupon.CouponPoliciesResponse;
import store.buzzbook.front.dto.product.ProductResponse;

@Controller
@RequiredArgsConstructor
public class HomeController {

	private final CouponPolicyClient couponPolicyClient;
	private final ProductClient productClient;

	@GetMapping("/")
	public String goHome() {
		return "redirect:/home";
	}

	@GetMapping("/admin")
	public String goManage() {
		return "redirect:/admin/home";
	}

	@GetMapping("home")
	public String home(Model model) {
		CouponPoliciesResponse couponPolicies = couponPolicyClient.getCouponPoliciesByScope(
			List.of("global", "book", "category"));

		//양 적으면 숫자 추가하세요
		List<ProductResponse> latestProducts = productClient.getLatestProduct(8);

		// 랜덤 상품 선택
		ProductResponse randomProduct = getRandomProduct(latestProducts);

		model.addAttribute("page", "main");
		model.addAttribute("title", "메인페이지");
		model.addAttribute("couponPolicies", couponPolicies);
		model.addAttribute("latestProducts", latestProducts);
		model.addAttribute("randomProduct", randomProduct);
		return "index";
	}

	private final Random random = new Random();

	private ProductResponse getRandomProduct(List<ProductResponse> products) {
		if (products.isEmpty()) {
			return null;
		}
		int randomIndex = random.nextInt(products.size());
		return products.get(randomIndex);
	}
}

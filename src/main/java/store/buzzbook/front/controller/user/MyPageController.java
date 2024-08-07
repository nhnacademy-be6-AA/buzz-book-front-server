package store.buzzbook.front.controller.user;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import store.buzzbook.front.common.annotation.JwtValidate;
import store.buzzbook.front.dto.user.AddressInfoResponse;
import store.buzzbook.front.dto.user.ChangePasswordRequest;
import store.buzzbook.front.dto.user.UpdateUserRequest;
import store.buzzbook.front.dto.user.UserInfo;
import store.buzzbook.front.service.jwt.JwtService;
import store.buzzbook.front.service.user.UserService;

@RequiredArgsConstructor
@Controller
@RequestMapping("/mypage")
@Slf4j
public class MyPageController {
	private final UserService userService;

	@JwtValidate
	@GetMapping
	public String myPage(Model model, HttpServletRequest request) {
		Long userId = (Long)request.getAttribute(JwtService.USER_ID);

		UserInfo userInfo = userService.getUserInfo(userId);

		model.addAttribute("title", "마이페이지");
		model.addAttribute("page", "mypage-index");
		model.addAttribute("fragment", "myInfo");
		model.addAttribute("user", userInfo);

		return "index";
	}

	@JwtValidate
	@GetMapping("/deactivate")
	public String deactivateForm(Model model) {
		model.addAttribute("title", "탈퇴");
		model.addAttribute("page", "deactivate");

		return "index";
	}

	@JwtValidate
	@GetMapping("/edit")
	public String editForm(Model model, HttpServletRequest request) {
		Long userId = (Long)request.getAttribute(JwtService.USER_ID);
		UserInfo userInfo = userService.getUserInfo(userId);

		model.addAttribute("title", "정보 수정");
		model.addAttribute("page", "mypage-index");
		model.addAttribute("fragment", "info-edit");
		model.addAttribute("user", userInfo);

		return "index";
	}

	@JwtValidate
	@GetMapping("/password")
	public String changePasswordForm(Model model) {
		model.addAttribute("title", "비밀번호 변경");
		model.addAttribute("page", "mypage-index");
		model.addAttribute("fragment", "change-password");

		return "index";
	}

	@JwtValidate
	@PostMapping("/edit")
	public String edit(HttpServletRequest request, Model model,
		@Valid @ModelAttribute UpdateUserRequest updateUserRequest) {
		Long userId = (Long)request.getAttribute(JwtService.USER_ID);
		userService.updateUserInfo(userId, updateUserRequest);

		return "redirect:/mypage";
	}

	@JwtValidate
	@PostMapping("/password")
	public String changePassword(HttpServletRequest request, @Valid ChangePasswordRequest changePasswordRequest) {
		Long userId = (Long)request.getAttribute(JwtService.USER_ID);
		userService.changePassword(userId, changePasswordRequest);

		return "redirect:/mypage";
	}

	@JwtValidate
	@GetMapping("/coupons")
	public String coupons(@RequestParam(defaultValue = "all") String couponStatusName, Model model) {
		model.addAttribute("coupons", userService.getUserCoupons(couponStatusName));
		model.addAttribute("title", "쿠폰내역");
		model.addAttribute("page", "mypage-index");
		model.addAttribute("fragment", "mypage-coupons");

		return "index";
	}

	@JwtValidate
	@GetMapping("/points")
	public String points(@PageableDefault(page = 0, size = 10) Pageable pageable, Model model) {
		model.addAttribute("points", userService.getUserPoints(pageable));
		model.addAttribute("title", "포인트내역");
		model.addAttribute("page", "mypage-index");
		model.addAttribute("fragment", "mypage-points");
		return "index";
	}

	@JwtValidate
	@GetMapping("/addresses")
	public String getAddressList(Model model, HttpServletRequest request) {
		List<AddressInfoResponse> addressList = userService.getAddressList();
		model.addAttribute("title", "주소관리");
		model.addAttribute("page", "mypage-index");
		model.addAttribute("fragment", "mypage-address");
		model.addAttribute("addressList", addressList);

		return "index";
	}

	@JwtValidate
	@DeleteMapping("/addresses")
	public String deleteAddress(@RequestParam("addressId") Long addressId) {

		userService.deleteAddress(addressId);

		return "redirect:/mypage/addresses";
	}
}

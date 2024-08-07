package store.buzzbook.front.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import store.buzzbook.front.common.annotation.GuestOnly;
import store.buzzbook.front.dto.user.RegisterUserRequest;
import store.buzzbook.front.service.user.UserService;

@Controller
@Slf4j
@RequiredArgsConstructor
public class RegisterController {
	private final UserService userService;

	@GuestOnly
	@GetMapping("/signup")
	public String registerForm() {
		return "pages/register/signup";
	}

	@GuestOnly
	@PostMapping("/signup")
	public String registerSubmit(@ModelAttribute @Valid RegisterUserRequest registerUserRequest) {
		log.info("회원가입 요청 id : {}", registerUserRequest.loginId());

		userService.registerUser(registerUserRequest);

		log.debug("회원가입 성공 리다이렉션");
		return "redirect:/welcome?id=" + registerUserRequest.loginId();
	}

	@GetMapping("/welcome")
	public String welcome(@RequestParam("id")String id, Model model) {
		model.addAttribute("id", id);
		return "pages/register/signup-success";
	}


}

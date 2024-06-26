package store.buzzbook.front.client.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import store.buzzbook.front.dto.coupon.DownloadCouponRequest;
import store.buzzbook.front.dto.user.LoginUserResponse;
import store.buzzbook.front.dto.user.RegisterUserApiRequest;
import store.buzzbook.front.dto.user.RegisterUserResponse;
import store.buzzbook.front.dto.user.UserInfo;

@FeignClient(name = "userClient", url = "http://${api.core.host}:" + "${api.core.port}/api/account")
public interface UserClient {

	@PostMapping("/register")
	public RegisterUserResponse registerUser(RegisterUserApiRequest registerUserApiRequest);

	@PostMapping("/login")
	public LoginUserResponse requestLogin(String loginId);

	@PutMapping("/login")
	public UserInfo successLogin(String loginId);

	@PostMapping("/coupons/download")
	public void downloadCoupon(@RequestBody DownloadCouponRequest request);
}

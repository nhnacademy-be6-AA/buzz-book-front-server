package store.buzzbook.front.dto.coupon;

import jakarta.validation.constraints.Min;
import lombok.Builder;

@Builder
public record DownloadCouponRequest(

	@Min(value = 0, message = "유저 번호는 0 보다 작을 수 없습니다.")
	long userId,

	@Min(value = 0, message = "쿠폰 정책 번호는 0 보다 작을 수 없습니다.")
	int couponPolicyId
) {
	public static DownloadCouponRequest create(long userId, int couponPolicyId) {
		return new DownloadCouponRequest(userId, couponPolicyId);
	}
}

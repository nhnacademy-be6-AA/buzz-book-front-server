package store.buzzbook.front.dto.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewRequest {

	@NotBlank(message = "내용을 입력하세요.")
	private String content;

	@NotNull(message = "점수는 1점에서 10점 자연수값만 입력가능합니다.")
	@Min(value = 1, message = "점수는 최소 1점이어야 합니다.")
	@Max(value = 10, message = "점수는 최대 10점이어야 합니다.")
	private int reviewScore;

	@NotNull(message = "주문한 상품에만 리뷰를 달 수 있습니다.")
	private long orderDetailId;
}

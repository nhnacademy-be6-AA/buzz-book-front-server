package store.buzzbook.front.dto.payment;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class ReadPaymentKeyWithOrderDetailRequest {
	@NotNull
	private long orderDetailId;
	@Nullable
	private String orderEmail;
}

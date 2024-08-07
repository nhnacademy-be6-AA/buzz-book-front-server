package store.buzzbook.front.dto.payment;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public abstract class PayResult {
	@JsonProperty("method")
	private String method;
	@JsonProperty("totalAmount")
	private int totalAmount;
	@JsonProperty("paymentKey")
	private String paymentKey;
	@JsonProperty("orderId")
	private String orderId;

	public PayResult(String method, int totalAmount, String paymentKey, String orderId) {
		this.method = method;
		this.totalAmount = totalAmount;
		this.paymentKey = paymentKey;
		this.orderId = orderId;
	}
}

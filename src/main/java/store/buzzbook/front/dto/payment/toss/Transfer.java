package store.buzzbook.front.dto.payment.toss;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Transfer {
	private String bankCode;
	private String settlementStatus;
}
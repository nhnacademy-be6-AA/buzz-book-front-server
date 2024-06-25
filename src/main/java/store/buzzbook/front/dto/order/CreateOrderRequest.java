package store.buzzbook.front.dto.order;

import java.time.ZonedDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import store.buzzbook.front.dto.user.UserInfo;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CreateOrderRequest {
	private String orderStr;
	private int price;
	private String request;
	private String address;
	private String addressDetail;
	private int zipcode;
	private ZonedDateTime desiredDeliveryDate;
	private String receiver;
	private int deliveryPolicyId;
	private String loginId;
	private List<CreateOrderDetailRequest> details;
}

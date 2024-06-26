package store.buzzbook.front.dto.order;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
	private String desiredDeliveryDate;
	private String receiver;
	private Integer deliveryPolicyId;
	private String loginId;
	private List<CreateOrderDetailRequest> details;
	private String contactNumber;
	private String email;
	private Integer orderStatusId;
	private String sender;
	private String receiverContactNumber;
}

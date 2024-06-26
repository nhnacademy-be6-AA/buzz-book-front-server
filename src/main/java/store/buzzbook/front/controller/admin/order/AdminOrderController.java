package store.buzzbook.front.controller.admin.order;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import store.buzzbook.front.dto.order.ReadOrderDetailResponse;
import store.buzzbook.front.dto.order.ReadOrdersRequest;
import store.buzzbook.front.dto.order.UpdateOrderDetailRequest;
import store.buzzbook.front.dto.order.UpdateOrderRequest;
import store.buzzbook.front.dto.payment.ReadBillLogRequest;
import store.buzzbook.front.dto.payment.ReadBillLogWithoutOrderResponse;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/orders")
public class AdminOrderController {
	@Value("${api.core.host}")
	private String host;

	@Value("${api.core.port}")
	private int port;

	@GetMapping
	public String adminOrderPage(Model model, @RequestParam int page, @RequestParam int size, HttpSession session) {
		if (page < 1) {
			page = 1;
		}
		ReadOrdersRequest orderRequest = new ReadOrdersRequest();
		orderRequest.setLoginId("parkseol");
		orderRequest.setPage(page);
		orderRequest.setSize(size);

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");

		HttpEntity<ReadOrdersRequest> readOrderRequestHttpEntity = new HttpEntity<>(orderRequest, headers);

		ResponseEntity<Map> response = restTemplate.exchange(
			String.format("http://%s:%d/api/orders/list", host, port), HttpMethod.POST, readOrderRequestHttpEntity, Map.class);

		if (response.getBody().get("total").toString().equals("0")){
			return "redirect:/order-manage?page=" + (page-1) +"&size=10";
		}

		model.addAttribute("page", "order-manage");

		model.addAttribute("myOrders", response.getBody().get("responseData"));
		model.addAttribute("total", response.getBody().get("total"));
		model.addAttribute("currentPage", page);
		model.addAttribute("title", "주문관리자페이지");

		return "admin/index";
	}

	@GetMapping("/{id}")
	public String updateStatus(Model model, @PathVariable int id, @RequestParam int page, @RequestParam int size, @RequestParam String status, HttpSession session) {
		if (page < 1) {
			page = 1;
		}

		UpdateOrderRequest request = UpdateOrderRequest.builder().orderStatusName(status).id(id).loginId("parkseol").build();
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");

		HttpEntity<UpdateOrderRequest> updateOrderDetailRequestHttpEntity = new HttpEntity<>(request, headers);

		ResponseEntity<ReadOrderDetailResponse> response = restTemplate.exchange(
			String.format("http://%s:%d/api/orders", host, port), HttpMethod.PUT, updateOrderDetailRequestHttpEntity, ReadOrderDetailResponse.class);


		ReadOrdersRequest orderRequest = new ReadOrdersRequest();
		orderRequest.setLoginId("parkseol");
		orderRequest.setPage(page);
		orderRequest.setSize(size);

		HttpEntity<ReadOrdersRequest> readOrderRequestHttpEntity = new HttpEntity<>(orderRequest, headers);

		ResponseEntity<Map> readResponse = restTemplate.exchange(
			String.format("http://%s:%d/api/orders/list", host, port), HttpMethod.POST, readOrderRequestHttpEntity, Map.class);

		if (readResponse.getBody().get("total").toString().equals("0")){
			return "redirect:/admin/orders?page=" + (page-1) +"&size=10";
		}

		model.addAttribute("myOrders", readResponse.getBody().get("responseData"));
		model.addAttribute("total", readResponse.getBody().get("total"));
		model.addAttribute("currentPage", page);

		model.addAttribute("page", "order-manage");

		return "redirect:/admin/orders?page=" + page +"&size=10";
	}

	@GetMapping("detail/{id}")
	public String updateDetailStatus(Model model, @PathVariable int id, @RequestParam int page, @RequestParam int size, @RequestParam String status, HttpSession session) {
		if (page < 1) {
			page = 1;
		}

		UpdateOrderDetailRequest request = UpdateOrderDetailRequest.builder().orderStatusName(status).id(id).loginId("parkseol").build();
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");

		HttpEntity<UpdateOrderDetailRequest> updateOrderDetailRequestHttpEntity = new HttpEntity<>(request, headers);

		ResponseEntity<ReadOrderDetailResponse> response = restTemplate.exchange(
			String.format("http://%s:%d/api/orders/detail", host, port), HttpMethod.PUT, updateOrderDetailRequestHttpEntity, ReadOrderDetailResponse.class);


		ReadOrdersRequest orderRequest = new ReadOrdersRequest();
		orderRequest.setLoginId("parkseol");
		orderRequest.setPage(page);
		orderRequest.setSize(size);

		HttpEntity<ReadOrdersRequest> readOrderRequestHttpEntity = new HttpEntity<>(orderRequest, headers);

		ResponseEntity<Map> readResponse = restTemplate.exchange(
			String.format("http://%s:%d/api/orders/list", host, port), HttpMethod.POST, readOrderRequestHttpEntity, Map.class);

		model.addAttribute("myOrders", readResponse.getBody().get("responseData"));
		model.addAttribute("total", readResponse.getBody().get("total"));
		model.addAttribute("currentPage", page);

		model.addAttribute("page", "order-manage");

		return "redirect:/admin/orders?page=" + page +"&size=10";
	}

	@GetMapping("/billlog")
	public String adminBillLog(HttpSession session, Model model, @RequestParam String orderId) throws Exception {

		ReadBillLogRequest request = new ReadBillLogRequest(orderId, "parkseol");

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");

		HttpEntity<ReadBillLogRequest> readBillLogRequestHttpEntity = new HttpEntity<>(request, headers);

		ResponseEntity<List<ReadBillLogWithoutOrderResponse>> response = restTemplate.exchange(
			String.format("http://%s:%d/api/payments/bill-logs", host, port), HttpMethod.POST, readBillLogRequestHttpEntity, new ParameterizedTypeReference<List<ReadBillLogWithoutOrderResponse>>() {});

		model.addAttribute("adminBillLogs", response.getBody());
		model.addAttribute("page", "adminpayment");

		return "admin/index";
	}
}

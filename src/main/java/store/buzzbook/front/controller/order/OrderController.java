package store.buzzbook.front.controller.order;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import store.buzzbook.front.dto.order.CreateOrderRequest;
import store.buzzbook.front.dto.order.ReadOrderResponse;
import store.buzzbook.front.dto.order.ReadWrappingResponse;
import store.buzzbook.front.dto.user.AddressInfo;
import store.buzzbook.front.dto.user.UserInfo;

@Controller
public class OrderController {
    @GetMapping("/order")
    public String order(Model model) {
        model.addAttribute("page", "order");
        model.addAttribute("title", "주문하기");
        UserInfo userInfo = UserInfo.builder().name("PS").email("a@a.com").contactNumber("11111111111").build();
        model.addAttribute("myInfo", userInfo);
        List<AddressInfo> addressInfos = new ArrayList<>();
        addressInfos.add(AddressInfo.builder().id(1).addressName("우리집").build());
        model.addAttribute("addressInfos", addressInfos);
        CreateOrderRequest request = new CreateOrderRequest();
        request.setUserInfo(userInfo);
        model.addAttribute("createOrderRequest", request);
        List<ReadWrappingResponse> packages = new ArrayList<>();
        packages.add(ReadWrappingResponse.builder().paper("신문지").price(1000).build());
        model.addAttribute("packages", packages);

        return "index";
    }

    @GetMapping("/my-page")
    public String myPage(Model model) {
        Page<ReadOrderResponse> readOrderResponses = new PageImpl<>(new ArrayList<>());
        model.addAttribute("page", "my-page");
        model.addAttribute("myOrders", readOrderResponses);

        return "index";
    }
}

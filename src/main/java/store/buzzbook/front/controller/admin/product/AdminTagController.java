package store.buzzbook.front.controller.admin.product;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import store.buzzbook.front.client.product.TagClient;
import store.buzzbook.front.common.annotation.ProductJwtValidate;
import store.buzzbook.front.dto.product.TagResponse;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/tag")
public class AdminTagController {

	private final TagClient tagClient;


	@ProductJwtValidate
	@GetMapping
	public String adminTagsPage(
		@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
		@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
		@RequestParam(value = "tagName", required = false) String tagName,
		Model model
	) {
		ResponseEntity<Page<TagResponse>> response = tagClient.getAllTags(pageNo, pageSize, tagName);
		Page<TagResponse> tags = response.getBody();

		model.addAttribute("page","admin-product-tag");
		model.addAttribute("tags", tags.getContent());
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", tags.getTotalPages());
		model.addAttribute("pageSize", pageSize);

		return "admin/index";
	}

	@GetMapping("/all")
	@ResponseBody
	public List<TagResponse> getAllTags()
	{
		ResponseEntity<List<TagResponse>> response = tagClient.getAllTags();
		return response.getBody();
	}

	@PostMapping
	public String saveTag(@RequestParam("tagName") String tagName) {
		tagClient.saveTag(tagName);
		return "redirect:/admin/tag";
	}

	@PostMapping("/{id}")
	public String deleteTag(@PathVariable("id") int id) {
		tagClient.deleteTag(id);
		return "redirect:/admin/tag";
	}
}

package store.buzzbook.front.controller.admin.product;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import store.buzzbook.front.dto.product.BookApiRequest;
import store.buzzbook.front.service.product.BookService;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class AdminApiBook {

	private final BookService bookService;

	@GetMapping("/open")
	public String showApiBookPage() {
		return "admin/pages/product-manage-api-book";
	}

	@GetMapping("/search")
	public String searchBooks(@RequestParam(required = false, defaultValue = "") String query, Model model) {
		log.info("Search query: {}", query);
		List<BookApiRequest.Item> books = bookService.searchBooks(query);
		model.addAttribute("books", books);
		model.addAttribute("query", query);
		return "admin/pages/product-manage-api-book";
	}
}
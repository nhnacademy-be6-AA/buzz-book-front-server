package store.buzzbook.front.dto.product;

import java.time.LocalDate;
import java.util.List;

import org.springframework.lang.Nullable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import store.buzzbook.front.common.exception.product.ProductNotFoundException;

@Builder
@Data
public class BookResponse{
	@NotNull
	private long id;
	@NotBlank
	private String title;
	@NotBlank
	private List<String> authors;
	@Nullable
	private String description;
	@NotBlank
	@Size(min = 10, max = 13)
	private String isbn;
	@NotBlank
	private String publisher;
	@NotBlank
	private LocalDate publishDate;
	@Nullable
	private ProductResponse product;

	public void isProductBook() {
		if(product == null) {
			throw new ProductNotFoundException("이 상품은 책이 아닙니다.", new RuntimeException());
		}
	}
}

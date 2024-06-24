package store.buzzbook.front.dto.order;

import java.time.ZonedDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ReadOrderStatusResponse {
	private int id;
	private String name;
	private ZonedDateTime updateDate;
}
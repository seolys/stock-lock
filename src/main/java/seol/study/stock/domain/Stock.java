package seol.study.stock.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Stock {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long productId;

	private Long quantity;

	public Stock(final Long productId, final Long quantity) {
		this.productId = productId;
		this.quantity = quantity;
	}

	public void decrease(final Long quantity) {
		if(this.quantity - quantity < 0) {
			throw new RuntimeException("foo");
		}
		this.quantity = this.quantity - quantity;
	}
}

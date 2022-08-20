package seol.study.stock.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seol.study.stock.repository.StockRepository;

@Service
@RequiredArgsConstructor
public class StockService {

	private final StockRepository stockRepository;

	@Transactional
	public void decrease(final Long id, final Long quantity) {
		final var stock = stockRepository.findById(id).orElseThrow();
		stock.decrease(quantity);
	}

	@Transactional
	public synchronized void synchronizedDecreaseWithTransactional(final Long id, final Long quantity) {
		final var stock = stockRepository.findById(id).orElseThrow();
		stock.decrease(quantity);
	}

	public synchronized void synchronizedDecrease(final Long id, final Long quantity) {
		final var stock = stockRepository.findById(id).orElseThrow();
		stock.decrease(quantity);
		stockRepository.saveAndFlush(stock); // @Transactional을 생략했기때문에 JPA dirty checking update가 안되므로 수동 update.
	}

}

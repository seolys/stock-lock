package seol.study.stock.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import seol.study.stock.repository.StockRepository;

@Service
@RequiredArgsConstructor
public class NamedLockStockService {

	private final StockRepository stockRepository;

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void decrease(final Long id, final Long quantity) {
		final var stock = stockRepository.findById(id).orElseThrow();
		stock.decrease(quantity);
	}


}

package seol.study.stock.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seol.study.stock.repository.StockRepository;

@Service
@RequiredArgsConstructor
public class PessimisticLockStockService {

	private final StockRepository stockRepository;

	@Transactional
	public void decrease(final Long id, final Long quantity) {
		final var stock = stockRepository.findByIdWithPessimistLock(id);
		stock.decrease(quantity);
	}

}

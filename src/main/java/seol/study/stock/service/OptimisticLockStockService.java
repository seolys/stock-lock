package seol.study.stock.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seol.study.stock.repository.StockWithVersionRepository;

@Service
@RequiredArgsConstructor
public class OptimisticLockStockService {

	private final StockWithVersionRepository stockRepository;

	@Transactional
	public void decrease(final Long id, final Long quantity) {
		final var stock = stockRepository.findByIdWithOptimisticLock(id);
		stock.decrease(quantity);
	}

}

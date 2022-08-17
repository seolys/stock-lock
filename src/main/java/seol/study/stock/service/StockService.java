package seol.study.stock.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seol.study.stock.domain.Stock;
import seol.study.stock.repository.StockRepository;

@Service
@RequiredArgsConstructor
public class StockService {

		private final StockRepository stockRepository;

		@Transactional
		public void decrease(Long id, Long quantity) {
			final var stock = stockRepository.findById(id).orElseThrow();
			stock.decrease(quantity);
		}

}

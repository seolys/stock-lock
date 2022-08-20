package seol.study.stock.facade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import seol.study.stock.service.OptimisticLockStockService;

@Slf4j
@Component
@RequiredArgsConstructor
public class OptimisticLockStockFacade {

	private final OptimisticLockStockService stockService;

	public void decrease(final Long id, final Long quantity) throws InterruptedException {
		while (true) {
			try {
				stockService.decrease(id, quantity);
				break;
			} catch (final Exception e) {
				log.info("낙관적 락 충돌. 50millis 대기 후 재시도.");
				Thread.sleep(50);
			}
		}
	}

}

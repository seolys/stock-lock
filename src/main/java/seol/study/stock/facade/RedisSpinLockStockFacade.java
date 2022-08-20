package seol.study.stock.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import seol.study.stock.repository.RedisSpinLockRepository;
import seol.study.stock.service.StockService;

@Component
@RequiredArgsConstructor
public class RedisSpinLockStockFacade {

	private final RedisSpinLockRepository redisLockRepository;

	private final StockService stockService;

	public void decrease(final Long id, final Long quantity) throws InterruptedException {
		final var lockKey = makeLockKey(id);
		while (!redisLockRepository.lock(lockKey)) {
			Thread.sleep(100);
		}

		try {
			stockService.decrease(id, quantity);
		} finally {
			redisLockRepository.unlock(lockKey);
		}

	}

	private String makeLockKey(final Long id) {
		return "stock:" + id.toString();
	}

}

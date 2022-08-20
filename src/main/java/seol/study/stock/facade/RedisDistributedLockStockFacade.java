package seol.study.stock.facade;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;
import seol.study.stock.service.StockService;

@Component
@RequiredArgsConstructor
public class RedisDistributedLockStockFacade {

	private final RedissonClient redissonClient;

	private final StockService stockService;

	public void decrease(final Long id, final Long quantity) throws InterruptedException {
		final var lockKey = makeLockKey(id);

		RLock lock = null;
		try {
			lock = redissonClient.getLock(lockKey);
			final var available = lock.tryLock(5, 1, TimeUnit.SECONDS);
			if (!available) {
				System.out.println("lock 획득 실패");
				return;
			}
			stockService.decrease(id, quantity);
		} finally {
			if (lock != null && lock.isLocked() && lock.isHeldByCurrentThread()) {
				lock.unlock();
			}
		}
	}

	private String makeLockKey(final Long id) {
		return "stock:" + id.toString();
	}

}

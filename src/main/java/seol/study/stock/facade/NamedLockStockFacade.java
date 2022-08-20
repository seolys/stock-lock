package seol.study.stock.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import seol.study.stock.repository.NamedLockRepository;
import seol.study.stock.service.NamedLockStockService;

@Component
@RequiredArgsConstructor
public class NamedLockStockFacade {

	private final NamedLockRepository lockRepository;

	private final NamedLockStockService stockService;

	public void decrease(final Long id, final Long quantity) throws InterruptedException {
		final var lockKey = makeLockKey(id);
		try {
			lockRepository.getLock(lockKey);
			stockService.decrease(id, quantity);
		} finally {
			lockRepository.releaseLock(makeLockKey(id));
		}
	}

	private String makeLockKey(final Long id) {
		return "stock:" + id.toString();
	}

}

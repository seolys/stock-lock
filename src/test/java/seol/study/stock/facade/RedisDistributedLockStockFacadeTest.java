package seol.study.stock.facade;

import static org.assertj.core.api.Assertions.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import seol.study.stock.domain.Stock;
import seol.study.stock.repository.StockRepository;

@SpringBootTest
@DisplayName("재고 Redis Distributed Lock 서비스(Redisson)")
class RedisDistributedLockStockFacadeTest {

	@Autowired
	private RedisDistributedLockStockFacade stockFacade;
	@Autowired
	private StockRepository stockRepository;

	private Stock stock;

	@BeforeEach
	public void before() {
		stock = new Stock(1L, 100L);
		stockRepository.saveAndFlush(stock);
	}

	@AfterEach
	public void after() {
		stockRepository.deleteAll();
	}

	@Test
	public void 동시에_100개_요청() throws InterruptedException { // Race Condition
		final int threadCount = 100;
		final var executorService = Executors.newFixedThreadPool(32);
		final var latch = new CountDownLatch(threadCount);

		for (int i = 0; i < threadCount; i++) {
			executorService.submit(() -> {
				try {
					stockFacade.decrease(stock.getId(), 1L);
				} catch (final InterruptedException e) {
					throw new RuntimeException(e);
				} finally {
					latch.countDown();
				}
			});
		}
		latch.await();

		// then
		final var findStock = stockRepository.findById(stock.getId()).orElseThrow();
		assertThat(findStock.getQuantity()).isEqualTo(0L);
	}
}
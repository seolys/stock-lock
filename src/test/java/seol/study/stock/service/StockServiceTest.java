package seol.study.stock.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import seol.study.stock.domain.Stock;
import seol.study.stock.repository.StockRepository;
 
@SpringBootTest
class StockServiceTest {

	@Autowired
	private StockService stockService;
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
	public void stock_decrease() {
		// when
		stockService.decrease(stock.getId(), 1L);

		// then
		final var findStock = stockRepository.findById(stock.getId()).orElseThrow();
		assertThat(findStock.getQuantity()).isEqualTo(99L);
	}

}
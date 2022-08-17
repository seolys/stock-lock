package seol.study.stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import seol.study.stock.domain.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

}

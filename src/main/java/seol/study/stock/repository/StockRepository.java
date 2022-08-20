package seol.study.stock.repository;

import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import seol.study.stock.domain.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

	@Lock(value = LockModeType.PESSIMISTIC_WRITE)
	@Query("select s from Stock s where s.id = :id")
	Stock findByIdWithPessimistLock(@Param("id") Long id);

}

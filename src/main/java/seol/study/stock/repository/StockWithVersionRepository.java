package seol.study.stock.repository;

import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import seol.study.stock.domain.StockWithVersion;

@Repository
public interface StockWithVersionRepository extends JpaRepository<StockWithVersion, Long> {

	@Lock(value = LockModeType.OPTIMISTIC)
	@Query("select s from StockWithVersion s where s.id = :id")
	StockWithVersion findByIdWithOptimisticLock(@Param("id") Long id);
	
}

package seol.study.stock.repository;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisSpinLockRepository {

	private final RedisTemplate<String, String> redisTemplate;

	public Boolean lock(final String key) {
		return redisTemplate.opsForValue()
				.setIfAbsent(key, "lock", Duration.ofSeconds(3)); // setIfAbsent: setnx
	}

	public Boolean unlock(final String key) {
		return redisTemplate.delete(key);
	}

}

package nil.lld.rate_limiter.limiter;

import nil.lld.rate_limiter.RateLimitType;
import nil.lld.rate_limiter.model.RateLimitConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class FixedWindowRateLimiter extends RateLimiter {
    private final Map<String, Integer> requestCount = new ConcurrentHashMap<>();
    private final Map<String, Long> windowStart = new HashMap<>();

    public FixedWindowRateLimiter(RateLimitConfig config) {
        super(config, RateLimitType.FIXED_WINDOW);
    }

    @Override
    public boolean allowRequest(String userId) {
        AtomicBoolean allowed = new AtomicBoolean(false);

        long currentRequestWindow = System.currentTimeMillis() / 1000 / config.getWindowInSeconds();

        requestCount.compute(userId, (id, count) -> {
            long lastReqWindow = windowStart.getOrDefault(id, currentRequestWindow);

            if (lastReqWindow != currentRequestWindow) {
                //window expired -> reset counter and window of last req
                windowStart.put(id, currentRequestWindow);
                allowed.set(true);
                return 1; //1st req in window
            }

            if (count == null) count = 0;

            if (count < config.getMaxRequests()) {
                allowed.set(true);
                return count + 1; //incr count
            }

            return count; //remain same
        });

        return allowed.get();
    }

}

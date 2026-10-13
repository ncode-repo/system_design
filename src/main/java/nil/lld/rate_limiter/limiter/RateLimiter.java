package nil.lld.rate_limiter.limiter;

import lombok.AllArgsConstructor;
import nil.lld.rate_limiter.RateLimitType;
import nil.lld.rate_limiter.model.RateLimitConfig;

@AllArgsConstructor
public abstract class RateLimiter {
    protected final RateLimitConfig config;
    protected final RateLimitType type;

    public abstract boolean allowRequest(String userId);
}

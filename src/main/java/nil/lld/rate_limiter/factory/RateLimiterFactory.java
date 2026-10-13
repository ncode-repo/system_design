package nil.lld.rate_limiter.factory;

import nil.lld.rate_limiter.RateLimitType;
import nil.lld.rate_limiter.limiter.FixedWindowRateLimiter;
import nil.lld.rate_limiter.limiter.RateLimiter;
import nil.lld.rate_limiter.limiter.SlidingWindowLogLimiter;
import nil.lld.rate_limiter.limiter.TokenBucketRateLimiter;
import nil.lld.rate_limiter.model.RateLimitConfig;

public class RateLimiterFactory {
    public static RateLimiter createRateLimiter(RateLimitType type, RateLimitConfig config) {
        return switch (type) {
            case FIXED_WINDOW -> new FixedWindowRateLimiter(config);
            case SLIDING_WINDOW_LOG -> new SlidingWindowLogLimiter(config);
            case TOKEN_BUCKET -> new TokenBucketRateLimiter(config);
            default -> throw new IllegalArgumentException("Unsupported rate limit type: " + type);
        };
    }
}

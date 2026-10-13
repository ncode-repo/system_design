package nil.lld.rate_limiter.service;

import nil.lld.rate_limiter.RateLimitType;
import nil.lld.rate_limiter.enums.UserTier;
import nil.lld.rate_limiter.factory.RateLimiterFactory;
import nil.lld.rate_limiter.limiter.RateLimiter;
import nil.lld.rate_limiter.model.RateLimitConfig;
import nil.lld.rate_limiter.model.User;

import java.util.HashMap;
import java.util.Map;

public class RateLimiterService {
    private final Map<UserTier, RateLimiter> rateLimiters = new HashMap<>();

    public RateLimiterService() {
        rateLimiters.put(UserTier.FREE,
                RateLimiterFactory.createRateLimiter(RateLimitType.TOKEN_BUCKET, new RateLimitConfig(10, 60)));

        rateLimiters.put(UserTier.PREMIUM,
                RateLimiterFactory.createRateLimiter(RateLimitType.FIXED_WINDOW, new RateLimitConfig(100, 60)));
    }

    public boolean allowRequest(User user) {
        RateLimiter rateLimiter = rateLimiters.get(user.getTier());
        if (rateLimiter == null) {
            throw new IllegalArgumentException("No rate limiter configured for user tier: " + user.getTier());
        }
        return rateLimiter.allowRequest(user.getUserId());
    }
}

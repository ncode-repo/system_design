package nil.lld.rate_limiter;

import nil.lld.rate_limiter.enums.UserTier;
import nil.lld.rate_limiter.model.User;
import nil.lld.rate_limiter.service.RateLimiterService;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        RateLimiterService rateLimiterService = new RateLimiterService();

        User freeUser = new User("user1", UserTier.FREE);
        User premiumUser = new User("user2", UserTier.PREMIUM);

        System.out.println("Testing Free User Rate Limiting:");
        for (int i = 1; i <= 15; i++) {
            boolean allowed = rateLimiterService.allowRequest(freeUser);
            System.out.println("Request " + i + " for Free User: " + (allowed ? "Allowed" : "Blocked"));
            Thread.sleep(100); // Sleep for 100ms between requests
        }

        System.out.println("\nTesting Premium User Rate Limiting:");
        for (int i = 1; i <= 105; i++) {
            boolean allowed = rateLimiterService.allowRequest(premiumUser);
            System.out.println("Request " + i + " for Premium User: " + (allowed ? "Allowed" : "Blocked"));
            Thread.sleep(100); // Sleep for 100ms between requests
        }
    }
}
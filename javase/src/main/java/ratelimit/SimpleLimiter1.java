package ratelimit;

import java.util.concurrent.TimeUnit;

/**
 * @author neilfoc
 * @Description 令牌桶容量是1，限流速率事1秒1个
 * 方法：reserve
 * @Date 2022/6/26
 */
class SimpleLimiter1 {
    // 下一令牌产生时间
    long next = System.nanoTime();
    // 发放令牌间隔：纳秒
    long interval = 1000_000_000;

    // 预占令牌，返回能够获取令牌的时间
    synchronized long reserve(long now) {
        // 请求时间在下一令牌产生时间之后
        // 重新计算下一令牌产生时间
        if (now > next) {
            // 将下一令牌产生时间重置为当前时间
            next = now;
        }
        // 能够获取令牌的时间
        long at = next;
        // 设置下一令牌产生时间
        next += interval;
        // 返回线程需要等待的时间
        return Math.max(at, 0L);
    }

    // 申请令牌
    void acquire() {
        // 申请令牌时的时间
        long now = System.nanoTime();
        // 预占令牌
        long at = reserve(now);
        long waitTime = Long.max(at - now, 0);
        // 按照条件等待
        if (waitTime > 0) {
            try {
                TimeUnit.NANOSECONDS.sleep(waitTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

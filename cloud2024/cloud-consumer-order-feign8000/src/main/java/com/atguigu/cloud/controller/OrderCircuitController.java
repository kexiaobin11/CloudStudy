package com.atguigu.cloud.controller;

import com.atguigu.cloud.apis.PayFeignApi;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author kexiaobin
 */
@RestController
@RequestMapping("feign")
public class OrderCircuitController {
    @Resource
    private PayFeignApi payFeignApi;

    @GetMapping("/pay/circuit/{id}")
    @CircuitBreaker(name = "cloud-payment-service", fallbackMethod = "myCircuitBreakerFallback")
    public String myCircuitBreaker(@PathVariable("id") Integer id) {
        return payFeignApi.myCircuit(id);
    }

//    @GetMapping("/pay/bulkhead/{id}")
//    @Bulkhead(name = "cloud-payment-service", fallbackMethod = "myCircuitBreakerFallback", type = Bulkhead.Type.SEMAPHORE)
//    public String myBulkhead(@PathVariable("id") Integer id) {
//        return payFeignApi.myBulkHead(id);
//    }

    /**
     * 隔离 threadPool
     * @param id
     * @return
     */
    @GetMapping("/pay/bulkhead/{id}")
    @Bulkhead(name = "cloud-payment-service", fallbackMethod = "myBulkheadPoolFallback", type = Bulkhead.Type.THREADPOOL)
    public CompletableFuture<String> myBulkheadPool(@PathVariable("id") Integer id) {
        System.out.println(Thread.currentThread().getName() + "\t" + "----开始进入");
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "\t" + "----准备离开");
        return CompletableFuture.supplyAsync(() -> payFeignApi.myBulkHead(id) + "\t" + "Bulkhead.Type.THREADPOOL");
    }

    @GetMapping(value = "/pay/ratelimit/{id}")
    @RateLimiter(name = "cloud-payment-service", fallbackMethod = "myRateLimitFallback")
    public String myRateLimit(@PathVariable("id") Integer id) {
        return this.payFeignApi.myRatelimit(id);
    }

    public String myCircuitBreakerFallback(Throwable t) {
        return "myCircuitBreakerFallback， 系统繁忙或运行出错，请稍后再试！";
    }

    public String myBulkheadFallback(Throwable t) {
        return "myBulkheadFallback， 系统繁忙或运行出错，请稍后再试！";
    }

    public CompletableFuture<String> myBulkheadPoolFallback(Throwable t) {
        return CompletableFuture.supplyAsync(() ->  "myBulkheadPoolFallback， 系统繁忙或运行出错，请稍后再试！");
    }

    public String myRateLimitFallback(Throwable t) {
        return "myRateLimitFallback， 系统繁忙或运行出错，请稍后再试！";
    }
}

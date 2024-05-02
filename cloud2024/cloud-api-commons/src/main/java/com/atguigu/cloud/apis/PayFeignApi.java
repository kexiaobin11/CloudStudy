package com.atguigu.cloud.apis;

import com.atguigu.cloud.entities.PayDto;
import com.atguigu.cloud.resp.ResultData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;
/**
 * @author kexiaobin
 */
@FeignClient(value = "cloud-payment-service")
public interface PayFeignApi {
    @PostMapping(value = "/pay/add")
    public ResultData addOrder(@RequestBody PayDto payDto);

    @GetMapping(value = "/pay/getById/{id}")
    public ResultData getPayInfo(@PathVariable("id") Long id);

    @GetMapping(value = "/pay/getAll")
    public ResultData<List<PayDto>> getAll();
}

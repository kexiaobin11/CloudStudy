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
    ResultData<PayDto> addOrder(@RequestBody PayDto payDto);

    @GetMapping(value = "/pay/getById/{id}")
    ResultData<PayDto> getPayInfo(@PathVariable("id") Integer  id);

    @GetMapping(value = "/pay/getAll")
    ResultData<List<PayDto>> getAll();

    @GetMapping(value = "/pay/circuit/{id}")
    String myCircuit(@PathVariable("id") Integer id);
}

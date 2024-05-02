package com.atguigu.cloud.controller;

import com.atguigu.cloud.apis.PayFeignApi;
import com.atguigu.cloud.entities.PayDto;
import com.atguigu.cloud.resp.ResultData;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @author kexiaobin
 */
@RestController
@RequestMapping("feign")
public class OrderController {
    @Resource
    private PayFeignApi payFeignApi;

    @PostMapping("/pay/add")
    public ResultData<PayDto> addOrder(@RequestBody PayDto payDto) {
        return payFeignApi.addOrder(payDto);
    }

    @GetMapping(value = "/pay/getById/{id}")
    public ResultData<PayDto> getPayInfo(@PathVariable("id") Integer id) {
        return payFeignApi.getPayInfo(id);
    }

    @GetMapping(value = "/pay/getAll")
    public ResultData<List<PayDto>> getAll() {
        return payFeignApi.getAll();
    }
}

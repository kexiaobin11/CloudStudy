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
@RequestMapping("fegin")
public class OrderController {
    @Resource
    private PayFeignApi payFeignApi;

    @PostMapping("/pay/add")
    public ResultData addOrder(@RequestBody PayDto payDto) {
        ResultData resultData = payFeignApi.addOrder(payDto);
        return resultData;
    }

    @GetMapping(value = "/pay/getById/{id}")
    public ResultData getPayInfo(@PathVariable("id") Long id) {
        ResultData resultData = payFeignApi.getPayInfo(id);
        return resultData;
    }

    @GetMapping(value = "/pay/getAll")
    public ResultData<List<PayDto>> getAll() {
        ResultData<List<PayDto>> pays = payFeignApi.getAll();
        return pays;
    }
}

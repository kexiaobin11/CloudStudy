package com.atguigu.cloud.controller;

import com.atguigu.cloud.entities.Pay;
import com.atguigu.cloud.entities.PayDto;
import com.atguigu.cloud.resp.ResultData;
import com.atguigu.cloud.service.PayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import util.BeanCopyUtil;

import java.util.*;

/**
 * @author kexiaobin
 */
@Tag(name = "支付微服务模块", description = "订单CRUD")
@Slf4j
@RestController
@RequestMapping("/pay")
public class PayController {
    @Value("${server.port}")
    private String port;

    @Resource
    private PayService payService;

    @PostMapping("/add")
    @Operation(summary = "新增", description = "新增支付流水, 参数是JSON字符串")
    public ResultData<Integer> addPay(@RequestBody PayDto payDTO) {
        try {
            Pay pay = new Pay();
            BeanUtils.copyProperties(payDTO, pay);
            return ResultData.success(payService.add(pay));
        } catch (Exception e) {
            return ResultData.fail(e.getMessage());
        }
    }

    @DeleteMapping("/del/{id}")
    @Operation(summary = "删除", description = "删除支付流水, 参数是Id")
    public ResultData<Integer> deletePay(@PathVariable("id") Integer id) {
        try {
            return ResultData.success(payService.delete(id));
        } catch (Exception e) {
            return ResultData.fail(e.getMessage());
        }
    }

    @PutMapping("/update")
    @Operation(summary = "更新", description = "更新支付流水, 参数是JSON字符串, 根据Id更新")
    public ResultData<Integer> updatePay(@RequestBody PayDto payDTO) {
        try {
            Pay pay = new Pay();
            BeanUtils.copyProperties(payDTO, pay);
            return ResultData.success(payService.update(pay));
        } catch (Exception e) {
            return ResultData.fail(e.getMessage());
        }
    }

    @GetMapping("/getById/{id}")
    @Operation(summary = "查询单个", description = "查询支付流水, 参数是Id")
    public ResultData<PayDto> getById(@PathVariable("id") Integer id) {
        PayDto payDTO = new PayDto();
        BeanUtils.copyProperties(payService.getById(id), payDTO);
        return ResultData.success(payDTO);
    }

    @GetMapping("/getAll")
    @Operation(summary = "查询所有", description = "查询所有支付流水")
    public ResultData<List<PayDto>> getAll() {
        try {
            List<Pay> pays = payService.getAll();
            List<PayDto> payDTOs = BeanCopyUtil.copyListProperties(pays, PayDto::new);
            return ResultData.success(payDTOs);
        } catch (Exception e) {
            return ResultData.fail(e.getMessage());
        }
    }

    @GetMapping("/get/info")
    public String getInfoByConsul(@Value("${atguigu.info}") String atguiguInfo) {
        return "atguiguInfo:" + atguiguInfo + "  port:" + port;
    }
}

package com.atguigu.cloud.controller;

import com.atguigu.cloud.entities.PayDto;
import com.atguigu.cloud.resp.ResultData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author kexiaobin
 */
@RestController
@RequestMapping("/consumer")
public class OrderController {

    public static String PAYMENT_SERVICE_URL = "http://cloud-payment-service";
    private final RestTemplate restTemplate;
    private final DiscoveryClient discoveryClient;

    public OrderController(RestTemplate restTemplate, DiscoveryClient discoveryClient) {
        this.restTemplate = restTemplate;
        this.discoveryClient = discoveryClient;
    }


    @PostMapping("/pay/add")
    public Object addOrder(@RequestBody PayDto payDTO) {
        return restTemplate.postForObject(PAYMENT_SERVICE_URL + "/pay/add", payDTO, ResultData.class);
    }

    @DeleteMapping("/pay/del/{id}")
    public Object delOrder(@PathVariable("id") Integer id) {
        return restTemplate.exchange(PAYMENT_SERVICE_URL + "/pay/del/" + id, HttpMethod.DELETE, null, ResultData.class).getBody();
    }

    @PutMapping("/pay/update")
    public Object delOrder(@RequestBody PayDto payDTO) {
        return restTemplate.exchange(PAYMENT_SERVICE_URL + "/pay/update/", HttpMethod.PUT, new HttpEntity<>(payDTO), ResultData.class).getBody();
    }

    @GetMapping("/pay/get/{id}")
    public Object getPayInfo(@PathVariable("id") Integer id) {
        return restTemplate.getForObject(PAYMENT_SERVICE_URL + "/pay/get/" + id, ResultData.class, id);
    }

    @GetMapping("/pay/getAll")
    public Object getPayListInfo() {
        return restTemplate.getForObject(PAYMENT_SERVICE_URL + "/pay/getAll", ResultData.class);
    }

    @GetMapping("/pay/getInfo")
    public Object getInfo() {
        return restTemplate.getForObject(PAYMENT_SERVICE_URL + "/pay/getInfo", ResultData.class);
    }

    @GetMapping("/discovery")
    public String discovery() {
        List<String> services = this.discoveryClient.getServices();
        for (String service : services) {
            System.out.println(service);
        }

        List<ServiceInstance> instances = discoveryClient.getInstances("cloud-payment-service");
        for (ServiceInstance instance : instances) {
            System.out.println(instance.getServiceId() + "\t" + instance.getHost() + "\t" + instance.getPort() + "\t" + instance.getUri());
        }

        return instances.get(0).getServiceId() + ":" + instances.get(0).getPort();
    }
}

package com.atguigu.cloud.service;

import com.atguigu.cloud.entities.Pay;

import java.util.*;
/**
 * @author kexiaobin
 */
public interface PayService {
    public int add(Pay pay);

    public int delete(Integer id);

    public int update(Pay pay);

    public List<Pay> getAll();

    public Pay getById(Integer id);
}

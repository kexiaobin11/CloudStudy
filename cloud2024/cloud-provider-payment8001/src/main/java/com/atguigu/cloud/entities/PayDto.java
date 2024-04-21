package com.atguigu.cloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayDto implements Serializable {
    private Integer id;

    private String payNo;

    private String orderNo;

    private Integer userId;

    private BigDecimal amount;
}

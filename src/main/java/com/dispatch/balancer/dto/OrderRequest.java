package com.dispatch.balancer.dto;

import com.dispatch.balancer.model.Order;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {

    private List<Order> orders;
}

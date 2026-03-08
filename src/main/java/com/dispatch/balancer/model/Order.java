package com.dispatch.balancer.model;


import lombok.Data;

@Data
public class Order {

    private String orderId;
    private double latitude;
    private double longitude;
    private String address;
    private double packageWeight;
    private Priority priority;
}

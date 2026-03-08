package com.dispatch.balancer.model;


import lombok.Data;

import java.util.List;

@Data
public class DispatchPlan {
    private String vehicleId;
    private double totalLoad;
    private String totalDistance;
    private List<Order> assignedOrders;

}

package com.dispatch.balancer.model;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Vehicle {
    private String vehicleId;
    private double capacity;

    private double currentLatitude;
    private double currentLongitude;
    private String currentAddress;

    private double currentLoad = 0;
    private double totalDistance = 0;

    private List<Order> assignedOrders = new ArrayList<>();
}

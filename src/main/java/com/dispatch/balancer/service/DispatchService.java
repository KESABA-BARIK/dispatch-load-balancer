package com.dispatch.balancer.service;

import com.dispatch.balancer.model.*;
import java.util.*;
import org.springframework.stereotype.Service;

@Service
public class DispatchService {
    private final List<Order> orders = new ArrayList<>();
    private final List<Vehicle> vehicles = new ArrayList<>();

    private final OptimizationService optimizationService;

    public DispatchService(OptimizationService optimizationService) {
        this.optimizationService = optimizationService;
    }

    public void addOrders(List<Order> orderList) {
        orders.addAll(orderList);
    }

    public void addVehicles(List<Vehicle> vehicleList) {
        vehicles.addAll(vehicleList);
    }

    public List<DispatchPlan> generatePlan() {
        
        return optimizationService.optimize(orders, vehicles);
    }
}

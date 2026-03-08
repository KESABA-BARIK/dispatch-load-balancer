package com.dispatch.balancer.service;

import com.dispatch.balancer.model.*;
import org.springframework.stereotype.Service;
import com.dispatch.balancer.util.HaversineUtil;

import java.util.*;

@Service
public class OptimizationService {
    public List<DispatchPlan> optimize(List<Order> orders, List<Vehicle> vehicles) {

        orders.sort(Comparator.comparingInt(order -> switch (order.getPriority()) {
            case HIGH -> 1;
            case MEDIUM -> 2;
            case LOW -> 3;
        }));

        for (Order order : orders) {

            Vehicle bestVehicle = null;
            double minDistance = Double.MAX_VALUE;

            for (Vehicle vehicle : vehicles) {

                if (vehicle.getCurrentLoad() + order.getPackageWeight()
                        <= vehicle.getCapacity()) {

                    double distance = HaversineUtil.calculateDistance(
                            vehicle.getCurrentLatitude(),
                            vehicle.getCurrentLongitude(),
                            order.getLatitude(),
                            order.getLongitude());

                    if (distance < minDistance) {
                        minDistance = distance;
                        bestVehicle = vehicle;
                    }
                }
            }

            if (bestVehicle != null) {

                double distance = HaversineUtil.calculateDistance(
                        bestVehicle.getCurrentLatitude(),
                        bestVehicle.getCurrentLongitude(),
                        order.getLatitude(),
                        order.getLongitude());

                bestVehicle.getAssignedOrders().add(order);

                bestVehicle.setCurrentLoad(
                        bestVehicle.getCurrentLoad() + order.getPackageWeight());

                bestVehicle.setTotalDistance(
                        bestVehicle.getTotalDistance() + distance);

                // update vehicle location
                bestVehicle.setCurrentLatitude(order.getLatitude());
                bestVehicle.setCurrentLongitude(order.getLongitude());
            }
        }

        List<DispatchPlan> plans = new ArrayList<>();

        for (Vehicle vehicle : vehicles) {

            DispatchPlan plan = new DispatchPlan();

            plan.setVehicleId(vehicle.getVehicleId());
            plan.setTotalLoad(vehicle.getCurrentLoad());
            double roundedDistance =Math.round(vehicle.getTotalDistance() * 100.0) / 100.0;
            plan.setTotalDistance(roundedDistance+"km");
            plan.setAssignedOrders(vehicle.getAssignedOrders());

            plans.add(plan);
        }

        return plans;
    }
}

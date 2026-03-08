package com.dispatch.balancer.service;

import com.dispatch.balancer.model.Order;
import com.dispatch.balancer.model.Priority;
import com.dispatch.balancer.model.Vehicle;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OptimizationServiceTest {
    private final OptimizationService service = new OptimizationService();

    @Test
    void shouldAssignOrderToVehicle() {

        Order order = new Order();
        order.setOrderId("ORD1");
        order.setLatitude(12.97);
        order.setLongitude(77.59);
        order.setPackageWeight(10);
        order.setPriority(Priority.HIGH);

        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleId("V1");
        vehicle.setCapacity(50);
        vehicle.setCurrentLatitude(12.97);
        vehicle.setCurrentLongitude(77.59);

        var plans = service.optimize(List.of(order), List.of(vehicle));

        assertEquals(1, plans.get(0).getAssignedOrders().size());
    }
}

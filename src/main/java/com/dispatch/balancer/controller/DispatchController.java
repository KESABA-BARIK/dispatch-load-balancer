package com.dispatch.balancer.controller;


import com.dispatch.balancer.service.DispatchService;
import org.springframework.web.bind.annotation.*;
import com.dispatch.balancer.model.*;
import com.dispatch.balancer.dto.*;
import java.util.*;

@RestController
@RequestMapping("/api/dispatch")
public class DispatchController {
    private final DispatchService dispatchService;

    public DispatchController(DispatchService dispatchService) {
        this.dispatchService = dispatchService;
    }

    @PostMapping("/orders")
    public ApiResponse addOrders(@RequestBody OrderRequest request) {

        dispatchService.addOrders(request.getOrders());

        return new ApiResponse(
                "Delivery orders accepted",
                "Success"
        );
    }

    @PostMapping("/vehicles")
    public ApiResponse addVehicles(@RequestBody VehicleRequest request) {

        dispatchService.addVehicles(request.getVehicles());

        return new ApiResponse(
                "Vehicle details accepted",
                "Success"
        );
    }

    @GetMapping("/plan")
    public List<DispatchPlan> getDispatchPlan() {

        return dispatchService.generatePlan();
    }
}

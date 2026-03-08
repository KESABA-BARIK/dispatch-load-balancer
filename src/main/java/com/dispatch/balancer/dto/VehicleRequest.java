package com.dispatch.balancer.dto;


import com.dispatch.balancer.model.Vehicle;
import lombok.Data;

import java.util.List;

@Data
public class VehicleRequest {
    private List<Vehicle> vehicles;
}

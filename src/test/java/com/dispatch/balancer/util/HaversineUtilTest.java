package com.dispatch.balancer.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HaversineUtilTest {
    @Test
    void shouldCalculateDistanceCorrectly() {

        double distance = HaversineUtil.calculateDistance(
                12.9716,
                77.5946,
                12.9721,
                77.5937
        );

        assertTrue(distance > 0);
    }
}

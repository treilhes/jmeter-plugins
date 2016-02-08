package com.blazemeter.jmeter;

import org.junit.Test;

import java.util.Arrays;

public class WeightedSwitchControllerTest {

    @Test
    public void testComb() {
        long[] sums = new long[]{0L, 0L};
        long[] prio = new long[]{2, 3};
        long[] choices = new long[]{0, 0};
        for (int i = 0; i < 100; i++) {
            // inc sums
            for (int k = 0; k < sums.length; k++) {
                sums[k] += prio[k];
            }

            if (sums[0] >= sums[1]) {
                // 2
                sums[0] -= prio[0];
                choices[0]++;
            } else {
                // 3
                sums[1] -= prio[1];
                choices[1]++;
            }

            // minimize nums
            long min;
            if (sums[0] >= sums[1]) {
                min = sums[1];
            } else {
                min = sums[0];
            }
            for (int z = 0; z < sums.length; z++) {
                sums[z] -= min;
            }

        }
        System.out.print(Arrays.toString(choices));
    }
}
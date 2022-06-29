package com.diptanshu.quantummod1.block.custom;

import net.minecraft.world.level.block.Block;

// DOES WORK
public class QubitBlock extends Block {

    public QubitBlock(Properties properties) {
        super(properties);
    }

    public static double alpha = 1.0;
    public static double beta = Math.sqrt(1-Math.pow(alpha,2));
    public static double[] stateVector = {alpha, beta};

    public double[] returnQState() {
        return stateVector;
    }
}

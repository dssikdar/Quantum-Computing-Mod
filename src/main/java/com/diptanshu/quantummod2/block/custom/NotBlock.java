package com.diptanshu.quantummod2.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import static com.diptanshu.quantummod2.block.custom.QubitBlock.*;

public class NotBlock extends GateBlock {
    public NotBlock(Boolean pressed, Properties properties) {
        super(properties);
    }

    // Create the X-gate Matrix using a 2d double array
    final double[][] xMatrix = {{0.0, 1.0}, {1.0, 0.0}};

    @Override
    protected double[] apply(double[] statevector){
        return matrixMult(statevector, xMatrix);
    }
}

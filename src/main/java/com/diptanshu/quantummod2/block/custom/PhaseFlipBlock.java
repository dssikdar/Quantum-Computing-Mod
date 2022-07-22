package com.diptanshu.quantummod2.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import static com.diptanshu.quantummod2.block.custom.QubitBlock.matrixMult;

public class PhaseFlipBlock extends GateBlock {
    public PhaseFlipBlock(Boolean pressed, Properties properties) {
        super(properties);
    }

    // Create the Z-gate Matrix using a 2d double array
    double[][] zMatrix = { {1.0, 0.0}, {0.0, -1.0}};

    @Override
    public double[] apply(double[] statevector){
        return matrixMult(statevector, zMatrix);
    }
}

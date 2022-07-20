package com.diptanshu.quantummod2.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import static com.diptanshu.quantummod2.block.custom.QubitBlock.matrixMult;

public class HadamardBlock extends GateBlock {
    public HadamardBlock(Boolean pressed, Properties properties) {
        super(pressed, properties);
    }

    // Create the H-gate Matrix using a 2d double array
    final double k = 1/(Math.sqrt(2));
    final double[][] hadamardMatrix = {{k, k}, {k, -k}};

    /** Purpose: When you place the Hadamard Gate Block, you must click on it to apply the gate to the qubit
     * register or qubit block. You can place the H-gate in any direction, and this procedure will detect it
     * and change the state accordingly.
     * @param blockState
     * @param level
     * @param position
     * @return void
     */


}

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
    public void press(BlockState blockState, Level level, BlockPos position) {
        Block blockBelow = level.getBlockState(position.below(1)).getBlock();
        checkAndUpdate(level, blockBelow, "up");

        Block blockFacingNorth = level.getBlockState(position.north(1)).getBlock();
        checkAndUpdate(level, blockFacingNorth, "south");

        Block blockFacingSouth = level.getBlockState(position.south(1)).getBlock();
        checkAndUpdate(level, blockFacingSouth, "north");

        Block blockFacingEast = level.getBlockState(position.east(1)).getBlock();
        checkAndUpdate(level, blockFacingEast, "west");

        Block blockFacingWest = level.getBlockState(position.west(1)).getBlock();
        checkAndUpdate(level, blockFacingWest, "east");

        super.press(blockState, level, position);
    }

    public void checkAndUpdate(Level level, Block directionalBlock, String face) {
        if (level.isClientSide()) {
            if (directionalBlock instanceof QubitBlock) {
                QubitBlock qubitBlock = (QubitBlock) directionalBlock;
                qubitBlock.stateVector = matrixMult(qubitBlock.stateVector, hadamardMatrix);
            }
            if (directionalBlock instanceof QubitRegisterBlock) {
                QubitRegisterBlock registerBlock = (QubitRegisterBlock) directionalBlock;
                registerBlock.qRegStateVector.replace(face,
                        matrixMult(registerBlock.qRegStateVector.get(face), hadamardMatrix));
            }
        }
    }
}

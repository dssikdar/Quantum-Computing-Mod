package com.diptanshu.quantummod2.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import static com.diptanshu.quantummod2.block.custom.QubitBlock.matrixMult;

public class PhaseFlipBlock extends GateBlock {
    public PhaseFlipBlock(Boolean pressed, Properties properties) {
        super(pressed, properties);
    }

    // Create the Z-gate Matrix using a 2d double array
    double[][] zMatrix = { {1.0, 0.0}, {0.0, -1.0}};

    /** Purpose: When you place the Phase Flip Gate Block, you must click on it to apply the gate to the qubit
     * register or qubit block. You can place the Z-gate in any direction, and this procedure will detect it
     * and change the state accordingly.
     * @param blockState
     * @param level
     * @param position
     * @return void
     */
    public void press(BlockState blockState, Level level, BlockPos position) {

        BlockPos below = position.below(1);
        Block blockBelow = level.getBlockState(below).getBlock();
        checkAndUpdate(level, blockBelow, below, "up");

        BlockPos above = position.above(1);
        Block blockAbove = level.getBlockState(below).getBlock();
        checkAndUpdate(level, blockAbove, above, "down");

        BlockPos north = position.north(1);
        Block blockNorth = level.getBlockState(north).getBlock();
        checkAndUpdate(level, blockNorth, north, "south");

        BlockPos south = position.south(1);
        Block blockSouth = level.getBlockState(south).getBlock();
        checkAndUpdate(level, blockSouth, south, "north");

        BlockPos west = position.west(1);
        Block blockWest = level.getBlockState(west).getBlock();
        checkAndUpdate(level, blockWest, west, "east");

        BlockPos east = position.east(1);
        Block blockEast = level.getBlockState(east).getBlock();
        checkAndUpdate(level, blockEast, east, "west");

        super.press(blockState, level, position);
    }

    public void checkAndUpdate(Level level, Block directionalBlock, BlockPos pos, String face) {
        if (level.isClientSide()) {
            if (directionalBlock instanceof QubitBlock) {
                QubitBlock qubitBlock = (QubitBlock) directionalBlock;
                qubitBlock.setStateVector(matrixMult(qubitBlock.getStateVector(pos), zMatrix), pos);
            }
            if (directionalBlock instanceof QubitRegisterBlock) {
                QubitRegisterBlock registerBlock = (QubitRegisterBlock) directionalBlock;
                registerBlock.qRegStateVector.replace(face,
                        matrixMult(registerBlock.qRegStateVector.get(face), zMatrix));
            }
        }
    }
}

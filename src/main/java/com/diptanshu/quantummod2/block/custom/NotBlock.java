package com.diptanshu.quantummod2.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import static com.diptanshu.quantummod2.block.custom.QubitBlock.*;

public class NotBlock extends GateBlock {
    public NotBlock(Boolean pressed, Properties properties) {
        super(pressed, properties);
    }

    // Create the X-gate Matrix using a 2d double array
    final double[][] xMatrix = {{0.0, 1.0}, {1.0, 0.0}};

    /** Purpose: When you place the Not Gate Block, you must click on it to apply the gate to the qubit
     * register or qubit block. You can place the X-gate in any direction, and this procedure will detect it
     * and change the state accordingly.
     * @param blockState
     * @param level
     * @param position
     * @return void
     */
    public void press(BlockState blockState, Level level, BlockPos position) {

        BlockPos below = position.below();
        Block blockBelow = level.getBlockState(below).getBlock();
        checkAndUpdate(level, blockBelow, below, "up");

        BlockPos above = position.above();
        Block blockAbove = level.getBlockState(above).getBlock();
        checkAndUpdate(level, blockAbove, above, "down");

        BlockPos north = position.north();
        Block blockNorth = level.getBlockState(north).getBlock();
        checkAndUpdate(level, blockNorth, north, "south");

        BlockPos south = position.south();
        Block blockSouth = level.getBlockState(south).getBlock();
        checkAndUpdate(level, blockSouth, south, "north");

        BlockPos west = position.west();
        Block blockWest = level.getBlockState(west).getBlock();
        checkAndUpdate(level, blockWest, west, "east");

        BlockPos east = position.east();
        Block blockEast = level.getBlockState(east).getBlock();
        checkAndUpdate(level, blockEast, east, "west");

        super.press(blockState, level, position);

        super.press(blockState, level, position);
    }

    public void checkAndUpdate(Level level, Block directionalBlock, BlockPos pos, String face) {
        if (level.isClientSide()) {
            if (directionalBlock instanceof QubitBlock) {
                QubitBlock qubitBlock = (QubitBlock) directionalBlock;
                qubitBlock.setStateVector(matrixMult(qubitBlock.getStateVector(pos), xMatrix), pos);
            }
            if (directionalBlock instanceof QubitRegisterBlock) {
                QubitRegisterBlock registerBlock = (QubitRegisterBlock) directionalBlock;
                registerBlock.qRegStateVector.replace(face,
                        matrixMult(registerBlock.qRegStateVector.get(face), xMatrix));
            }
        }
    }
}

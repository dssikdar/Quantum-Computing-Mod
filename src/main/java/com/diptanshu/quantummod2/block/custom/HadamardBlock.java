package com.diptanshu.quantummod2.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import static com.diptanshu.quantummod2.block.custom.QubitBlock.matrixMult;
import static com.diptanshu.quantummod2.block.custom.QubitBlock.qubitPosition;

public class HadamardBlock extends GateBlock {
    public HadamardBlock(Boolean pressed, Properties properties) {
        super(pressed, properties);
    }

    double k = 1/(Math.sqrt(2));
    double[][] hadamardMatrix = {{k, k}, {k, -k}};

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


    // -----------------------------------------------------------------------------

    /**
    @Override
    public void press(BlockState blockState, Level level, BlockPos position) {
        Block surroundingBlock = level.getBlockState(qubitPosition).getBlock();

        if (level.isClientSide()) {
            if (surroundingBlock instanceof QubitBlock) {
                QubitBlock qubitBlock = QubitBlock.class.cast(surroundingBlock);
                double[] currentState = qubitBlock.stateVector.clone();
                qubitBlock.stateVector = matrixMult(currentState, hadamardMatrix);
            }
        }

        super.press(blockState, level, position);
    }
    */
}

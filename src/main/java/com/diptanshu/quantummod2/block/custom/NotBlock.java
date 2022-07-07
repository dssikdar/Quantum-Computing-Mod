package com.diptanshu.quantummod2.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import static com.diptanshu.quantummod2.block.custom.QubitBlock.matrixMult;

// IMPORTANT: DIRECTION is FACING EAST for NOT GATE.
public class NotBlock extends GateBlock {
    public NotBlock(Boolean pressed, Properties properties) {
        super(pressed, properties);
    }

    double[][] xMatrix = {{0.0, 1.0}, {1.0, 0.0}};

    @Override
    public void press(BlockState blockState, Level level, BlockPos position) {
        Block surroundingBlock = level.getBlockState(position.east(1)).getBlock();

        if (level.isClientSide()) {
            if (surroundingBlock instanceof QubitBlock) {
                QubitBlock qubitBlock = QubitBlock.class.cast(surroundingBlock);
                double[] currentState = qubitBlock.stateVector.clone();
                qubitBlock.stateVector = matrixMult(currentState, xMatrix);
            }
        }

        super.press(blockState, level, position);
    }
}

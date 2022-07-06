package com.diptanshu.quantummod2.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class NotBlock extends GateBlock {
    public NotBlock(Boolean pressed, Properties properties) {
        super(pressed, properties);
    }
    double[][] xMatrix = { {0.0, 1.0}, {1.0, 0.0}};
    public double[][] getMatrix() {
        return xMatrix;
    }

    /**
    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        QubitBlock.updateStateVector(xMatrix);
        Player player = pContext.getPlayer();
        player.sendMessage(new TextComponent((QubitBlock.stateVector[0]) + " |0>   AND   " + (QubitBlock.stateVector[1]) + " |1>"), player.getUUID());
        return super.getStateForPlacement(pContext);
    }
    */
}

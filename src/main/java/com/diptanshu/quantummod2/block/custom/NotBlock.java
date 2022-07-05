package com.diptanshu.quantummod2.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class NotBlock extends GateBlock {
    public NotBlock(Boolean pressed, Properties properties) {
        super(pressed, properties);
    }
    double[][] xMatrix = { {0.0, 1.0}, {1.0, 0.0}};
    public double[][] getMatrix() {
        return xMatrix;
    }
}

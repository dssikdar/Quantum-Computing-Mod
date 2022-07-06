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

// OLD VERSION WORKS; code below is not updated to newest system
public class HadamardBlock extends GateBlock {
    public HadamardBlock(Boolean pressed, Properties properties) {
        super(pressed, properties);
    }

    double k = 1/(Math.sqrt(2));
    double[][] hadamardMatrix = { {k, k}, {k, -k}};

    public double[][] getMatrix() {
        return hadamardMatrix;
    }
}

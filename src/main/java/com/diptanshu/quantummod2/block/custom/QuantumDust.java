package com.diptanshu.quantummod2.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.state.BlockState;

import static com.diptanshu.quantummod2.block.custom.QubitBlock.*;

public class QuantumDust extends RedStoneWireBlock {
    public QuantumDust(Properties properties) {
        super(properties);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext placeContext) {
        Level level = placeContext.getLevel();
        Player player = placeContext.getPlayer();

        if (level.isClientSide()) {
            Block thisBlock = level.getBlockState(qubitPosition).getBlock();
            if (thisBlock instanceof QubitBlock) {
                QubitBlock qubitBlock = QubitBlock.class.cast(thisBlock);
                printState(level, player, qubitBlock.stateVector, "Carried");
            }

        }

        return super.getStateForPlacement(placeContext);
    }
}

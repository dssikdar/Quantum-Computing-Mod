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
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.Map;

import static com.diptanshu.quantummod2.block.custom.QubitBlock.*;

public class QuantumDust extends RedStoneWireBlock implements QubitReferencer {
    public QuantumDust(Properties properties) {
        super(properties);
    }

    public static Map<Integer, BlockPos> POS_HASH_TO_QUBIT = new HashMap<Integer, BlockPos>();

    // FIXME this is purposefully bad code which only detects a single directly neighbouring qubit
    public BlockPos neighbouringQubit(BlockPos position, Level level){

        BlockPos below = position.below();
        Block blockBelow = level.getBlockState(below).getBlock();
        if(blockBelow instanceof QubitReferencer)
            return ((QubitReferencer) blockBelow).supplyQubit(below);

        BlockPos above = position.above();
        Block blockAbove = level.getBlockState(above).getBlock();
        if(blockAbove instanceof QubitReferencer)
            return ((QubitReferencer) blockAbove).supplyQubit(above);

        BlockPos north = position.north();
        Block blockNorth = level.getBlockState(north).getBlock();
        if(blockNorth instanceof QubitReferencer)
            return ((QubitReferencer) blockNorth).supplyQubit(north);

        BlockPos south = position.south();
        Block blockSouth = level.getBlockState(south).getBlock();
        if(blockSouth instanceof QubitReferencer)
            return ((QubitReferencer) blockSouth).supplyQubit(south);

        BlockPos west = position.west();
        Block blockWest = level.getBlockState(west).getBlock();
        if(blockWest instanceof QubitReferencer)
            return ((QubitReferencer) blockWest).supplyQubit(west);

        BlockPos east = position.east();
        Block blockEast = level.getBlockState(east).getBlock();
        if(blockEast instanceof QubitReferencer)
            return ((QubitReferencer) blockEast).supplyQubit(east);

        return null;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext placeContext) {
        Level level = placeContext.getLevel();
        Player player = placeContext.getPlayer();
        BlockPos qubitPosition = neighbouringQubit(placeContext.getClickedPos(), level);

        if (level.isClientSide() && qubitPosition != null) {
            Block thisBlock = level.getBlockState(qubitPosition).getBlock();
            if (thisBlock instanceof QubitBlock) {
                QubitBlock qubitBlock = QubitBlock.class.cast(thisBlock);
                POS_HASH_TO_QUBIT.put(placeContext.getClickedPos().hashCode(), qubitPosition);
                printState(level, player, qubitBlock.getStateVector(qubitPosition), "Carried");
            }
        } else if(qubitPosition == null){
            player.sendMessage(new TextComponent("Carried nothing"), player.getUUID());
        }

        return super.getStateForPlacement(placeContext);
    }

    @Override
    public BlockPos supplyQubit(BlockPos pos) {
        return POS_HASH_TO_QUBIT.get(pos.hashCode());
    }
}
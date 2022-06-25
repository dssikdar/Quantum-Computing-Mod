package com.diptanshu.quantummod1.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class QuantumDust extends Block {
    public QuantumDust(Properties properties) {
        super(properties);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {

        BlockPos positionClicked = pContext.getClickedPos();
        Player player = pContext.getPlayer();
        double[] qstate = new double[2];
        //player.sendMessage(new TextComponent("Debug1"), player.getUUID());

        Block surroundingBlock = pContext.getLevel().getBlockState(positionClicked.north(1)).getBlock();

        int count = 2;
        while (surroundingBlock instanceof QuantumDust) {
            surroundingBlock = pContext.getLevel().getBlockState(positionClicked.north(count)).getBlock();
            count += 1;
        }

        if (surroundingBlock instanceof QubitBlock) {
            qstate = ((QubitBlock) surroundingBlock).returnQState();
        } else if (surroundingBlock instanceof NotBlock) {
            qstate = ((NotBlock) surroundingBlock).returnQState();
        }

        //QubitBlock originQubit = ((QubitBlock) surroundingBlock);


        //double[] qstate = originQubit.returnQState();
        //player.sendMessage(new TextComponent("Debug2"), player.getUUID());
        player.sendMessage(new TextComponent((Math.pow(qstate[0],2)*100) + "% 0  AND  " + (Math.pow(qstate[1],2)*100) + "% 1"), player.getUUID());
        returnQState(qstate);
        //originQubit.returnQState();

        return super.getStateForPlacement(pContext);
    }

    public double[] returnQState(double[] qstate) {
        return qstate;
    }
}

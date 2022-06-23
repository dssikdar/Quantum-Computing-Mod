package com.diptanshu.quantummod1.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class QuantumDust extends Block {
    public QuantumDust(Properties properties) {
        super(properties);
    }

    public double[] qstate = QubitBlock.stateVector;

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {

        BlockPos positionClicked = pContext.getClickedPos();
        Player player = pContext.getPlayer();
        //player.sendMessage(new TextComponent("Hola"), player.getUUID());

        Block surroundingBlock = pContext.getLevel().getBlockState(positionClicked.north(1)).getBlock();

        int count = 2;
        while (surroundingBlock instanceof QuantumDust) {
            surroundingBlock = pContext.getLevel().getBlockState(positionClicked.north(count)).getBlock();
            count += 1;
        }

        if (surroundingBlock instanceof QubitBlock) {
            //player.sendMessage(new TextComponent("Hello"), player.getUUID());
            player.sendMessage(new TextComponent((Math.pow(qstate[0],2)*100) + "% 0  AND  " + (Math.pow(qstate[1],2)*100) + "% 1"), player.getUUID());
        }
        return super.getStateForPlacement(pContext);
    }

    /**
    public double[] returnQState() {
        return qstate;
    }
     */
}

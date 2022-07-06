package com.diptanshu.quantummod2.item.custom;

import com.diptanshu.quantummod2.block.custom.QubitBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;

import static com.diptanshu.quantummod2.block.custom.QubitBlock.matrixMult;

public class HadamardGateItem extends Item
{
    public HadamardGateItem(Properties pProperties) {
        super(pProperties);
    }

    final double k = 1/Math.sqrt(2.0);
    double[][] hMatrix = { {k, k}, {k, -k}};

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        BlockPos positionClicked = pContext.getClickedPos();
        Player player = pContext.getPlayer();
        Block clickedBlock = pContext.getLevel().getBlockState(positionClicked).getBlock();

        if (clickedBlock instanceof QubitBlock) {
            QubitBlock qubitBlock = QubitBlock.class.cast(clickedBlock);
            double[] currentState = qubitBlock.stateVector.clone();
            player.sendMessage(new TextComponent(("Current State: " + currentState[0]) + " |0>   AND   " + (currentState[1]) + " |1>"), player.getUUID());
            qubitBlock.stateVector = matrixMult(currentState, hMatrix);
            player.sendMessage(new TextComponent(("Qubit Block: " + qubitBlock.stateVector[0]) + " |0>   AND   " + (qubitBlock.stateVector[1]) + " |1>"), player.getUUID());
        }

        return super.useOn(pContext);
    }
}

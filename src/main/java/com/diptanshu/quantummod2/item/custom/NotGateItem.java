package com.diptanshu.quantummod2.item.custom;

import com.diptanshu.quantummod2.block.custom.QubitBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import static com.diptanshu.quantummod2.block.custom.QubitBlock.*;

public class NotGateItem extends Item
{
    public NotGateItem(Properties pProperties) {
        super(pProperties);
    }

    double[][] xMatrix = { {0.0, 1.0}, {1.0, 0.0}};

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        BlockPos positionClicked = pContext.getClickedPos();
        Player player = pContext.getPlayer();
        Level pLevel = pContext.getLevel();
        Block clickedBlock = pLevel.getBlockState(positionClicked).getBlock();

        if (pLevel.isClientSide()) {
            if (clickedBlock instanceof QubitBlock) {
                QubitBlock qubitBlock = QubitBlock.class.cast(clickedBlock);

                double[] currentState = qubitBlock.stateVector.clone();
                //printState(pLevel, player, currentState, "Old");
                qubitBlock.stateVector = matrixMult(currentState, xMatrix);
                //printState(pLevel, player, qubitBlock.stateVector, "New");

                //Note: Uncomment 'printState' to observe states before and/or after applying X gate
            }
        }
        return super.useOn(pContext);
    }
}

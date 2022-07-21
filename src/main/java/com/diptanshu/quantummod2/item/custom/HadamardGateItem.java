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
import net.minecraft.world.level.block.state.BlockState;

import static com.diptanshu.quantummod2.block.custom.QubitBlock.*;

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
        Level pLevel = pContext.getLevel();
        BlockState clickedBlockState = pContext.getLevel().getBlockState(positionClicked);
        Block clickedBlock = clickedBlockState.getBlock();

        if (pLevel.isClientSide()) {
            if (clickedBlock instanceof QubitBlock) {
                QubitBlock qubitBlock = QubitBlock.class.cast(clickedBlock);

                double[] currentState = qubitBlock.getStateVector(positionClicked);
                //printState(pLevel, player, currentState, "Previous");
                qubitBlock.setStateVector(matrixMult(currentState, hMatrix), positionClicked);
                //printState(pLevel, player, qubitBlock.stateVector, "Current");

                //Note: Uncomment 'printState' to observe states before and/or after applying X gate
            }
        }

        return super.useOn(pContext);
    }
}

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

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.RoundingMode;

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
        Block clickedBlock = pContext.getLevel().getBlockState(positionClicked).getBlock();

        if (pLevel.isClientSide()) {
            if (clickedBlock instanceof QubitBlock) {
                QubitBlock qubitBlock = QubitBlock.class.cast(clickedBlock);

                double[] currentState = qubitBlock.stateVector.clone();
                //printState(pLevel, player, currentState, "Previous");
                qubitBlock.stateVector = matrixMult(currentState, hMatrix);
                //printState(pLevel, player, qubitBlock.stateVector, "Current");

                //Note: Uncomment 'printState' to observe states before and/or after applying X gate
            }
        }

        return super.useOn(pContext);
    }
}

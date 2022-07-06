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

        if (clickedBlock instanceof QubitBlock) {
            QubitBlock qubitBlock = QubitBlock.class.cast(clickedBlock);

            double[] currentState = qubitBlock.stateVector.clone(); //WORKS 2
            printState(pLevel, player, currentState, "Old"); //WORKS 2
            qubitBlock.stateVector = matrixMult(currentState, hMatrix); //WORKS 2
            printState(pLevel, player, qubitBlock.stateVector, "New"); //WORKS 2

            //player.sendMessage(new TextComponent(("Current State: " + currentState[0]) + " |0>   AND   " + (currentState[1]) + " |1>"), player.getUUID()); //WORKS
            //player.sendMessage(new TextComponent(" "), player.getUUID());

            //qubitBlock.stateVector[0] = matrixMult(currentState, hMatrix)[0];
            //qubitBlock.stateVector[1] = matrixMult(currentState, hMatrix)[1];
            //player.sendMessage(new TextComponent(("Qubit Block: " + qubitBlock.stateVector[0]) + " |0>   AND   " + (qubitBlock.stateVector[1]) + " |1>"), player.getUUID()); //WORKS

            //double[] currentState = matrixMult(qubitBlock.stateVector.clone(), hMatrix);
            //Array.set(qubitBlock.stateVector, 0, currentState[0]);
            //Array.set(qubitBlock.stateVector, 1, currentState[1]);

        }

        return super.useOn(pContext);
    }
}

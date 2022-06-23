package com.diptanshu.quantummod1.block.custom;

import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class NotBlock extends Block {
    public NotBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    double[][] xMatrix = { {0.0, 1.0}, {1.0, 0.0}};

    public static double[] qstate = QubitBlock.stateVector;

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {

        Player player = pContext.getPlayer();

        qstate = matrixMult(xMatrix, qstate);
        player.sendMessage(new TextComponent((Math.pow(qstate[0],2)*100) + "% 0  AND  " + (Math.pow(qstate[1],2)*100) + "% 1"), player.getUUID());

        return super.getStateForPlacement(pContext);
    }

    public double[] matrixMult(double[][] matrix, double[] vector) {
        double[] resultant = new double[vector.length];

        for (int i=0; i<matrix.length; i++) {
            double tempSum = 0.0;
            for (int j=0; j<matrix[0].length; j++) {
                tempSum += matrix[i][j]*vector[j];
            }
            resultant[i] = tempSum;
        }

        return resultant;
    }

    public static double[] returnQState() {
        return qstate;
    }
}

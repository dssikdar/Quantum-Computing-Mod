package com.diptanshu.quantummod1.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

// DOES NOT WORK
public class NotBlock extends Block {
    public NotBlock(Properties properties) {
        super(properties);
    }

    double[][] xMatrix = { {0.0, 1.0}, {1.0, 0.0}};
    double[] qstate = new double[2];

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {

        BlockPos positionClicked = pContext.getClickedPos();
        Player player = pContext.getPlayer();


        player.sendMessage(new TextComponent("Debug1"), player.getUUID());

        Block surroundingBlock = pContext.getLevel().getBlockState(positionClicked.north(1)).getBlock();
        int count = 2;
        while (!(surroundingBlock instanceof QuantumDust)) {
            surroundingBlock = pContext.getLevel().getBlockState(positionClicked.north(count)).getBlock();
            count += 1;
            if (count > 10) {
                break;
            }
        }

        /**
        player.sendMessage(new TextComponent("Debug2"), player.getUUID());

        if (surroundingBlock instanceof QubitBlock) {
            qstate = ((QubitBlock) surroundingBlock).returnQState();
        }
        if (surroundingBlock instanceof QuantumDust) {
            qstate = ((QuantumDust) surroundingBlock).returnQState();
        }
        else if (surroundingBlock instanceof NotBlock) {
            qstate = ((NotBlock) surroundingBlock).returnQState();
        }
        else if (surroundingBlock instanceof HadamardBlock) {
            qstate = ((HadamardBlock) surroundingBlock).returnQState();
        }
         */

        player.sendMessage(new TextComponent("Debug3"), player.getUUID());

        qstate = ((QubitBlock) surroundingBlock).returnQState();

        qstate = matrixMult(xMatrix, qstate);
        player.sendMessage(new TextComponent((Math.pow(qstate[0],2)*100) + "% 0  AND  " + (Math.pow(qstate[1],2)*100) + "% 1"), player.getUUID());

        //NotBlock currentBlock = ((NotBlock) pContext.getLevel().getBlockState(positionClicked).getBlock());
        //currentBlock.returnQState();

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

    public double[] returnQState() {
        return qstate;
    }
}

package com.diptanshu.quantummod2.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

// DOES WORK
public class QubitBlock extends Block {

    public QubitBlock(Properties properties) {
        super(properties);
    }

    HadamardBlock hadamardGate = new HadamardBlock(false, properties);
    NotBlock notGate = new NotBlock(false, properties);
    double alpha = 1.0;
    double beta = 0.0;
    double[] stateVector = {alpha, beta};

    public void findGate (BlockPlaceContext pContext, BlockPos positionClicked) {
        Block thisBlock = pContext.getLevel().getBlockState(positionClicked.north(1)).getBlock();

        if (thisBlock instanceof HadamardBlock) {
            stateVector = updateState(stateVector, hadamardGate.getMatrix());
        } else if (thisBlock instanceof NotBlock) {
            stateVector = updateState(stateVector, notGate.getMatrix());
        }
    }

    public static double[] updateState(double[] quantumState, double[][] quantumGate) {
        return matrixMult(quantumGate, quantumState);
    }

    public static double[] matrixMult(double[][] matrix, double[] vector) {
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

    public static double[] scalarMult(double k, double[] vector) {
        double[] resultant = new double[vector.length];
        for (int i=0; i<vector.length; i++){
            resultant[i] = k*vector[i];
        }
        return resultant;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockPos positionClicked = pContext.getClickedPos();
        Player player = pContext.getPlayer();
        outputQuantumState(player);
        findGate(pContext, positionClicked);
        return super.getStateForPlacement(pContext);
    }

    private void outputQuantumState(Player player) {
        player.sendMessage(new TextComponent((stateVector[0]) + "|0>  AND  " + (stateVector[1]) + "|1>"), player.getUUID());
    }
}

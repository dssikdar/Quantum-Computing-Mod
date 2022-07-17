package com.diptanshu.quantummod2.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class QubitBlock extends Block {

    public QubitBlock(Properties properties) {
        super(properties);
    }

    public double alpha = 1.0;
    public double beta = 0.0;
    public double[] stateVector = {alpha, beta};

    public static BlockPos qubitPosition;

    public static double[] matrixMult(double[] vector, double[][] matrix) {
        double[] resultant = new double[vector.length];

        resultant[0] = round(matrix[0][0] * vector[0] + matrix[0][1] * vector[1], 3);
        resultant[1] = round(matrix[1][0] * vector[0] + matrix[1][1] * vector[1], 3);

        return resultant;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        Player player = pContext.getPlayer();
        Level pLevel = pContext.getLevel();
        qubitPosition = pContext.getClickedPos();
        stateVector[0] = 1.0;
        stateVector[1] = 0.0;
        printState(pLevel, player, stateVector, "Placement");
        return super.getStateForPlacement(pContext);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal number = new BigDecimal(Double.toString(value));
        number = number.setScale(places, RoundingMode.HALF_UP);
        return number.doubleValue();
    }

    public static void printState(Level level, Player player, double[] arr, String label) {
        if (level.isClientSide()) {
            player.sendMessage(new TextComponent((label + " state: " + arr[0]) + " |0>  +  " + (arr[1]) + " |1>"), player.getUUID());
        }
    }
}

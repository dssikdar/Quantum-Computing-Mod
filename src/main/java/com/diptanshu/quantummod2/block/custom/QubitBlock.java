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
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.Nullable;
import net.minecraft.world.level.block.state.properties.EnumProperty;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class QubitBlock extends Block implements QubitReferencer {

    public static final double[] defaultState = {1.0D, 0.0D};
    public static HashMap<Integer, double[]> POS_HASH_TO_VECTOR = new HashMap<Integer, double[]>();

    public QubitBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        Player player = pContext.getPlayer();
        Level pLevel = pContext.getLevel();
        BlockPos qubitPosition = pContext.getClickedPos();
        setStateVector(defaultState.clone(), qubitPosition);
        printState(pLevel, player, getStateVector(qubitPosition), "Placement");
        return super.getStateForPlacement(pContext);
    }

    public static double[] matrixMult(double[] vector, double[][] matrix) {
        double[] resultant = new double[vector.length];

        resultant[0] = round(matrix[0][0] * vector[0] + matrix[0][1] * vector[1], 3);
        resultant[1] = round(matrix[1][0] * vector[0] + matrix[1][1] * vector[1], 3);

        return resultant;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal number = new BigDecimal(Double.toString(value));
        number = number.setScale(places, RoundingMode.HALF_UP);
        return number.doubleValue();
    }

    public static void setStateVector(double[] newvector, BlockPos pos){
        POS_HASH_TO_VECTOR.put(pos.hashCode(), newvector);
    }
    public static double[] getStateVector(BlockPos pos){
        if(!POS_HASH_TO_VECTOR.containsKey(pos.hashCode())){
            POS_HASH_TO_VECTOR.put(pos.hashCode(), defaultState.clone());
        }
        return POS_HASH_TO_VECTOR.get(pos.hashCode());
    }

    public static void printState(Level level, Player player, double[] arr, String label) {
        if (level.isClientSide()) {
            player.sendMessage(new TextComponent((label + " state: " + arr[0]) + " |0>  +  " + (arr[1]) + " |1>"), player.getUUID());
        }
    }

    public BlockPos supplyQubit(BlockPos pos){
        return pos;
    }
}

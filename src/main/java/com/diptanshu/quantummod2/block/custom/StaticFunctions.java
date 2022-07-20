package com.diptanshu.quantummod2.block.custom;

import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.math.BigDecimal;
import java.math.RoundingMode;

public interface StaticFunctions {

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

    public static void printState(Level level, Player player, double[] arr, String label) {
        if (level.isClientSide()) {
            player.sendMessage(new TextComponent((label + " state: " + arr[0]) + " |0>  +  " + (arr[1]) + " |1>"), player.getUUID());
        }
    }


}

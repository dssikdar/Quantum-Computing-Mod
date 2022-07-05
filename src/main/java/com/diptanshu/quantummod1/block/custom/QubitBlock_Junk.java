package com.diptanshu.quantummod1.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class QubitBlock_Junk extends Block {
    public QubitBlock_Junk(Properties properties) {
        super(properties);
    }

    public static float alpha = (float) 0.5;
    public static float beta = (float) Math.sqrt(1-Math.pow(alpha,2));
    //public static double[] stateVector = {alpha, beta};

    @Override
    public float getShadeBrightness(BlockState p_48731_, BlockGetter getter, BlockPos pPos) {
        int maxLight = getter.getMaxLightLevel();
        float currentLight = alpha*maxLight;
        return currentLight;

        //return super.getShadeBrightness(p_48731_, getter, pPos);
    }
}

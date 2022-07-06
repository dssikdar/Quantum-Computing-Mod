package com.diptanshu.quantummod2.block.custom;

import net.minecraft.client.gui.components.Button;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.StoneButtonBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Properties;

public class GateBlock extends ButtonBlock {

    protected GateBlock(boolean pressed, BlockBehaviour.Properties properties) {
        super(pressed, properties);
    }

    protected SoundEvent getSound(boolean p_57062_) {
        return p_57062_ ? SoundEvents.STONE_BUTTON_CLICK_ON : SoundEvents.STONE_BUTTON_CLICK_OFF;
    }
}

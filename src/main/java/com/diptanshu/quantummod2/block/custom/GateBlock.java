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

import static com.diptanshu.quantummod2.block.custom.QubitBlock.matrixMult;

public abstract class GateBlock extends Block {
    public GateBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    protected abstract double[] apply(double[] statevector);

    protected SoundEvent getSound(boolean p_57062_) {
        return p_57062_ ? SoundEvents.STONE_BUTTON_CLICK_ON : SoundEvents.STONE_BUTTON_CLICK_OFF;
    }


    public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn){
        
        Block blockBelow = level.getBlockState(position.below(1)).getBlock();
        checkAndUpdate(level, blockBelow, "up");

        Block blockFacingNorth = level.getBlockState(position.north(1)).getBlock();
        checkAndUpdate(level, blockFacingNorth, "south");

        Block blockFacingSouth = level.getBlockState(position.south(1)).getBlock();
        checkAndUpdate(level, blockFacingSouth, "north");

        Block blockFacingEast = level.getBlockState(position.east(1)).getBlock();
        checkAndUpdate(level, blockFacingEast, "west");

        Block blockFacingWest = level.getBlockState(position.west(1)).getBlock();
        checkAndUpdate(level, blockFacingWest, "east");

        super.onBlockClicked(worldIn, pos,playerIn);
    }

    public void checkAndUpdate(Level level, Block directionalBlock, String face) {
        if (level.isClientSide()) {
            if (directionalBlock instanceof QubitBlock) {
                QubitBlock qubitBlock = (QubitBlock) directionalBlock;
                qubitBlock.stateVector = matrixMult(qubitBlock.stateVector, hadamardMatrix);
            }
            if (directionalBlock instanceof QubitRegisterBlock) {
                QubitRegisterBlock registerBlock = (QubitRegisterBlock) directionalBlock;
                registerBlock.qRegStateVector.replace(face,
                        matrixMult(registerBlock.qRegStateVector.get(face), hadamardMatrix));
            }
        }
    }

}

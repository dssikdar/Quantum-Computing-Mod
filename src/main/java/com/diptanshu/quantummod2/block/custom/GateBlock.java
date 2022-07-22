package com.diptanshu.quantummod2.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Random;

public abstract class GateBlock extends Block {
    public static final int pressDuration = 20;
    private static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public GateBlock(BlockBehaviour.Properties properties) {
        super(properties);
        // Set the default state to off
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(POWERED, false));
    }

    /**
     * Return the result of the gate being applied to a qubit. Each child class must implement this.
     * @param statevector - the qubit statevector to be modified
     */
    protected abstract double[] apply(double[] statevector);

    protected SoundEvent getSound(boolean p_57062_) {
        return p_57062_ ? SoundEvents.STONE_BUTTON_CLICK_ON : SoundEvents.STONE_BUTTON_CLICK_OFF;
    }

    // This is called on a right-click
    public InteractionResult use(BlockState state, Level level, BlockPos position, Player player, InteractionHand interactionHand, BlockHitResult hitResult) {
        if(!state.getValue(BlockStateProperties.POWERED)) {
            // Set state to on and schedule tick to be powered off
            level.setBlock(position, state.setValue(BlockStateProperties.POWERED, true), 3);
            level.scheduleTick(position, this, pressDuration);

            // Update all neighbouring blocks
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

            level.gameEvent(player, GameEvent.BLOCK_PRESS, position);
            // Button on sound
            level.playSound(player, position, getSound(true), SoundSource.BLOCKS, 0.3F, 0.6F);

            return InteractionResult.SUCCESS;
        } else{
            // Do nothing if already activated - this prevents super fast clicking
            return InteractionResult.CONSUME;
        }

    }

    // This is called when the de-activate scheduled tick happens
    @Override
    public void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, Random random){
        if(blockState.getValue(BlockStateProperties.POWERED)){
            serverLevel.setBlock(blockPos, blockState.setValue(BlockStateProperties.POWERED, false), 3);
            // Button off sound
            serverLevel.playSound(null, blockPos, getSound(false), SoundSource.BLOCKS, 0.3F, 0.6F);
            serverLevel.gameEvent(GameEvent.BLOCK_UNPRESS, blockPos);

        }
    }

    // Apply the gate to a neighbouring qubit
    public void checkAndUpdate(Level level, Block directionalBlock, String face) {
        if (level.isClientSide()) {
            if (directionalBlock instanceof QubitBlock) {
                QubitBlock qubitBlock = (QubitBlock) directionalBlock;
                qubitBlock.stateVector = apply(qubitBlock.stateVector);
            }
            if (directionalBlock instanceof QubitRegisterBlock) {
                QubitRegisterBlock registerBlock = (QubitRegisterBlock) directionalBlock;
                registerBlock.qRegStateVector.replace(face, apply(registerBlock.qRegStateVector.get(face)));
            }
        }
    }

    // This is necessary for the block to hold the POWERED state
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder){
        builder.add(POWERED);
    }

}

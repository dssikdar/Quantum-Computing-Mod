package com.diptanshu.quantummod2.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import static com.diptanshu.quantummod2.block.custom.QubitBlock.*;

public class NotBlock extends GateBlock {
    public NotBlock(Boolean pressed, Properties properties) {
        super(pressed, properties);
    }

    final double[][] xMatrix = {{0.0, 1.0}, {1.0, 0.0}};

    @Override
    public void press(BlockState blockState, Level level, BlockPos position) {
        /**
        Block blockBelow = level.getBlockState(position.below(1)).getBlock();
        if (level.isClientSide()) {
            if (blockBelow instanceof QubitBlock) {
                QubitBlock qubitBlock = (QubitBlock) blockBelow;
                qubitBlock.stateVector = matrixMult(qubitBlock.stateVector, xMatrix);
            }
            if (blockBelow instanceof QubitRegisterBlock) {
                QubitRegisterBlock registerBlock = (QubitRegisterBlock) blockBelow;
                registerBlock.qRegStateVector.replace("up",
                        matrixMult(registerBlock.qRegStateVector.get("up"), xMatrix));
            }
        }
        checkAndUpdate(level, blockBelow, "up");

        Block blockFacingNorth = level.getBlockState(position.north(1)).getBlock();
        if (level.isClientSide()) {
            if (blockFacingNorth instanceof QubitBlock) {
                QubitBlock qubitBlock = (QubitBlock) blockFacingNorth;
                qubitBlock.stateVector = matrixMult(qubitBlock.stateVector, xMatrix);
            }
            if (blockFacingNorth instanceof QubitRegisterBlock) {
                QubitRegisterBlock registerBlock = (QubitRegisterBlock) blockFacingNorth;
                registerBlock.qRegStateVector.replace("north",
                        matrixMult(registerBlock.qRegStateVector.get("north"), xMatrix));
            }
        }
        checkAndUpdate(level, blockFacingNorth, "north");

        Block blockFacingSouth = level.getBlockState(position.south(1)).getBlock();
        if (level.isClientSide()) {
            if (blockFacingSouth instanceof QubitBlock) {
                QubitBlock qubitBlock = (QubitBlock) blockFacingSouth;
                qubitBlock.stateVector = matrixMult(qubitBlock.stateVector, xMatrix);
            }
            if (blockFacingSouth instanceof QubitRegisterBlock) {
                QubitRegisterBlock registerBlock = (QubitRegisterBlock) blockFacingSouth;
                registerBlock.qRegStateVector.replace("south",
                        matrixMult(registerBlock.qRegStateVector.get("south"), xMatrix));
            }
        }
        checkAndUpdate(level, blockFacingSouth, "south");

        Block blockFacingEast = level.getBlockState(position.east(1)).getBlock();
        if (level.isClientSide()) {
            if (blockFacingEast instanceof QubitBlock) {
                QubitBlock qubitBlock = (QubitBlock) blockFacingEast;
                qubitBlock.stateVector = matrixMult(qubitBlock.stateVector, xMatrix);
            }
            if (blockFacingEast instanceof QubitRegisterBlock) {
                QubitRegisterBlock registerBlock = (QubitRegisterBlock) blockFacingEast;
                registerBlock.qRegStateVector.replace("east",
                        matrixMult(registerBlock.qRegStateVector.get("east"), xMatrix));
            }
        }
        checkAndUpdate(level, blockFacingEast, "east");

        Block blockFacingWest = level.getBlockState(position.west(1)).getBlock();
        if (level.isClientSide()) {
            if (blockFacingWest instanceof QubitBlock) {
                QubitBlock qubitBlock = (QubitBlock) blockFacingWest;
                qubitBlock.stateVector = matrixMult(qubitBlock.stateVector, xMatrix);
            }
            if (blockFacingWest instanceof QubitRegisterBlock) {
                QubitRegisterBlock registerBlock = (QubitRegisterBlock) blockFacingWest;
                registerBlock.qRegStateVector.replace("west",
                        matrixMult(registerBlock.qRegStateVector.get("west"), xMatrix));
            }
        }

         */

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

        super.press(blockState, level, position);
    }

    public void checkAndUpdate(Level level, Block directionalBlock, String face) {
        if (level.isClientSide()) {
            if (directionalBlock instanceof QubitBlock) {
                QubitBlock qubitBlock = (QubitBlock) directionalBlock;
                qubitBlock.stateVector = matrixMult(qubitBlock.stateVector, xMatrix);
            }
            if (directionalBlock instanceof QubitRegisterBlock) {
                QubitRegisterBlock registerBlock = (QubitRegisterBlock) directionalBlock;
                registerBlock.qRegStateVector.replace(face,
                        matrixMult(registerBlock.qRegStateVector.get(face), xMatrix));
            }
        }
    }
}

package com.diptanshu.quantummod2.item.custom;

import com.diptanshu.quantummod2.block.custom.QubitBlock;
import com.diptanshu.quantummod2.block.custom.QubitRegisterBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import static com.diptanshu.quantummod2.block.custom.QubitBlock.printState;

public class MeasurementItem extends Item
{
    public MeasurementItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        BlockPos positionClicked = pContext.getClickedPos();
        Direction directionClicked = pContext.getClickedFace();
        Player player = pContext.getPlayer();
        Level pLevel = pContext.getLevel();
        Block clickedBlock = pContext.getLevel().getBlockState(positionClicked).getBlock();
        String direction = "up";

        if (directionClicked == Direction.NORTH) {
            direction = "north";
        }
        else if (directionClicked == Direction.SOUTH) {
            direction = "south";
        }
        else if (directionClicked == Direction.EAST) {
            direction = "east";
        }
        else if (directionClicked == Direction.WEST) {
            direction = "west";
        }

        if (pLevel.isClientSide()) {
            if (clickedBlock instanceof QubitBlock) {
                QubitBlock qubitBlock = QubitBlock.class.cast(clickedBlock);
                printState(pLevel, player, qubitBlock.stateVector, "Current");
            }
            if (clickedBlock instanceof QubitRegisterBlock) {
                QubitRegisterBlock registerBlock = QubitRegisterBlock.class.cast(clickedBlock);
                player.sendMessage(new TextComponent("Direction: " + direction + " | " +
                        registerBlock.qRegisterState.get(direction)[0] + " |0> & " +
                        registerBlock.qRegisterState.get(direction)[1] + " |1>"), player.getUUID());
            }
        }

        return super.useOn(pContext);
    }
}

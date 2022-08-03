package com.diptanshu.quantummod2.item.custom;

import com.diptanshu.quantummod2.block.custom.QubitBlock;
import com.diptanshu.quantummod2.block.custom.QubitRegisterBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import static com.diptanshu.quantummod2.block.custom.QubitBlock.printState;

public class MeasurementItem extends Item
{
    public MeasurementItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        BlockPos positionClicked = pContext.getClickedPos();
        Direction faceClicked = pContext.getClickedFace();
        Player player = pContext.getPlayer();
        Level pLevel = pContext.getLevel();
        BlockState clickedBlockState = pContext.getLevel().getBlockState(positionClicked);
        Block clickedBlock = clickedBlockState.getBlock();

        String face = "up";

        if (faceClicked == Direction.NORTH) {
            face = "north";
        }
        else if (faceClicked == Direction.SOUTH) {
            face = "south";
        }
        else if (faceClicked == Direction.EAST) {
            face = "east";
        }
        else if (faceClicked == Direction.WEST) {
            face = "west";
        }

        if (pLevel.isClientSide()) {
            if (clickedBlock instanceof QubitBlock) {
                QubitBlock qubitBlock = QubitBlock.class.cast(clickedBlock);
                printState(pLevel, player, qubitBlock.getStateVector(positionClicked), "Current");
            }
            if (clickedBlock instanceof QubitRegisterBlock) {
                QubitRegisterBlock registerBlock = QubitRegisterBlock.class.cast(clickedBlock);
                player.sendMessage(new TextComponent("Direction: " + face + " | " +
                        registerBlock.getRegisterState(face)[0] + " |0> + " +
                        registerBlock.getRegisterState(face)[1] + " |1>"), player.getUUID());
                player.sendMessage(new TextComponent("List of Qubits: " + registerBlock.getListOfQubitFaces()), player.getUUID());

                /** Debug: Print full hashmap
                player.sendMessage(new TextComponent("UP: " + registerBlock.qRegStateVector.get("up")[0]), player.getUUID());
                player.sendMessage(new TextComponent("UP: " + registerBlock.qRegStateVector.get("up")[1]), player.getUUID());
                player.sendMessage(new TextComponent("NORTH: " + registerBlock.qRegStateVector.get("north")[0]), player.getUUID());
                player.sendMessage(new TextComponent("NORTH: " + registerBlock.qRegStateVector.get("north")[1]), player.getUUID());
                player.sendMessage(new TextComponent("SOUTH: " + registerBlock.qRegStateVector.get("south")[0]), player.getUUID());
                player.sendMessage(new TextComponent("SOUTH: " + registerBlock.qRegStateVector.get("south")[1]), player.getUUID());
                player.sendMessage(new TextComponent("EAST: " + registerBlock.qRegStateVector.get("east")[0]), player.getUUID());
                player.sendMessage(new TextComponent("EAST: " + registerBlock.qRegStateVector.get("east")[1]), player.getUUID());
                player.sendMessage(new TextComponent("WEST: " + registerBlock.qRegStateVector.get("west")[0]), player.getUUID());
                player.sendMessage(new TextComponent("WEST: " + registerBlock.qRegStateVector.get("west")[1]), player.getUUID());
                 */

                registerBlock.setTensorProduct(registerBlock.getTensorProduct());
                player.sendMessage(new TextComponent("Tensor Prod: " + registerBlock.getTensorProduct()), player.getUUID());
            }

        }

        return super.useOn(pContext);
    }
}

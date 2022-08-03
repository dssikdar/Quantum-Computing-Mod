package com.diptanshu.quantummod2.item.custom;

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

public class QubitAllocationItem extends Item
{
    public QubitAllocationItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        BlockPos positionClicked = pContext.getClickedPos();
        Direction faceClicked = pContext.getClickedFace();
        Player player = pContext.getPlayer();
        Level pLevel = pContext.getLevel();
        Block clickedBlock = pContext.getLevel().getBlockState(positionClicked).getBlock();

        String face = " ";

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
        else if (faceClicked == Direction.UP) {
            face = "up";
        }

        if (pLevel.isClientSide()) {
            if (clickedBlock instanceof QubitRegisterBlock) {
                QubitRegisterBlock registerBlock = QubitRegisterBlock.class.cast(clickedBlock);
                if (registerBlock.getListOfQubitFaces().contains(face)) {
                    player.sendMessage(new TextComponent("Already allocated"), player.getUUID());
                }
                else {
                    registerBlock.addToListOfQubitFaces(face);
                }
                player.sendMessage(new TextComponent("qRegs: " + registerBlock.getListOfQubitFaces()), player.getUUID());
                //registerBlock.qRegStateVector.replace(face, new double[]{1.0, 0.0});
                registerBlock.getRegisterState(face)[0] = 1.0;
                registerBlock.getRegisterState(face)[1] = 0.0;
                player.sendMessage(new TextComponent("Face: " + face + " | " +
                        registerBlock.getRegisterState(face)[0] + " |0> & " +
                        registerBlock.getRegisterState(face)[1] + " |1>"), player.getUUID());
            }
        }

        return super.useOn(pContext);
    }
}

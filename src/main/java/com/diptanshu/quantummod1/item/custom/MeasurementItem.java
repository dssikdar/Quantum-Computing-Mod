package com.diptanshu.quantummod1.item.custom;

import com.diptanshu.quantummod1.block.custom.QubitBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;


public class MeasurementItem extends Item
{
    public MeasurementItem(Properties pProperties) {
        super(pProperties);
    }

    //Hashtable<int[], Double> qstate = new Hashtable<int[], Double>();
    public static double[] qstate = QubitBlock.stateVector;

    @Override
    public InteractionResult useOn(UseOnContext pContext) {

        if(pContext.getLevel().isClientSide()) {
            BlockPos positionClicked = pContext.getClickedPos();
            Player player = pContext.getPlayer();
            // player.sendMessage(new TextComponent("Hi"), player.getUUID());

            Block block = pContext.getLevel().getBlockState(positionClicked).getBlock();
            if (block instanceof QubitBlock) {
                // player.sendMessage(new TextComponent("Hello"), player.getUUID());
                QubitBlock newBloch = QubitBlock.class.cast(block);
                newBloch.stateVector = QubitBlock.stateVector;
                if (checkBornRule(newBloch)) {
                    outputQuantumState(newBloch.stateVector, player);
                }
            }
        }

        return super.useOn(pContext);
    }

    private boolean checkBornRule(QubitBlock bloch) {
        double threshold = 0.0001;
        double sum = 0.0;
        for (int i=0; i<qstate.length; i++) {
            for (int j=0; j<qstate.length; j++) {
                sum += Math.pow(qstate[i],2);
            }
        }
        if (Math.abs(sum - 1.0) < threshold) {
            return true;
        }
        else {
            return false;
        }
    }

    private void outputQuantumState(double[] qstate, Player player) {
        player.sendMessage(new TextComponent((Math.pow(qstate[0],2)*100) + "% 0  AND  " + (Math.pow(qstate[1],2)*100) + "% 1"), player.getUUID());
    }
}

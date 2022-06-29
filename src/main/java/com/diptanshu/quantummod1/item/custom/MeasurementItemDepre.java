package com.diptanshu.quantummod1.item.custom;

import com.diptanshu.quantummod1.block.custom.QubitBlockDepre;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;

// DOES NOT WORK in the new system that was adapted for QubitBlock, QuantumDust, and NotBlock
public class MeasurementItemDepre extends Item
{
    public MeasurementItemDepre(Properties pProperties) {
        super(pProperties);
    }

    //public static double[] qstate = QubitBlock.stateVector;

    @Override
    public InteractionResult useOn(UseOnContext pContext) {

        if(pContext.getLevel().isClientSide()) {
            BlockPos positionClicked = pContext.getClickedPos();
            Player player = pContext.getPlayer();
            // player.sendMessage(new TextComponent("Hi"), player.getUUID());

            Block block = pContext.getLevel().getBlockState(positionClicked).getBlock();
            if (block instanceof QubitBlockDepre) {
                // player.sendMessage(new TextComponent("Hello"), player.getUUID());
                QubitBlockDepre newBlock = QubitBlockDepre.class.cast(block);
                if (checkBornRule(newBlock)) {
                    outputQuantumState(newBlock, player);
                }
            }
        }

        return super.useOn(pContext);
    }

    private boolean checkBornRule(QubitBlockDepre bloch) {
        double threshold = 0.0001;
        double[] qstate = bloch.returnQState();
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

    private void outputQuantumState(QubitBlockDepre bloch, Player player) {
        double[] qstate = bloch.returnQState();
        player.sendMessage(new TextComponent((Math.pow(qstate[0],2)*100) + "% 0  AND  " + (Math.pow(qstate[1],2)*100) + "% 1"), player.getUUID());
    }
}

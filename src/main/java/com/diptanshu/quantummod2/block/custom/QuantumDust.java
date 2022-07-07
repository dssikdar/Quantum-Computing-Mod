package com.diptanshu.quantummod2.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.state.BlockState;

import static com.diptanshu.quantummod2.block.custom.QubitBlock.matrixMult;

// IN PROGRESS -- DOESN'T DO ANYTHING YET
public class QuantumDust extends RedStoneWireBlock {
    public QuantumDust(Properties properties) {
        super(properties);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext placeContext) {
        Level level = placeContext.getLevel();
        BlockPos position = placeContext.getClickedPos();

        Block surroundingBlock = level.getBlockState(position.north(1)).getBlock();

        if (level.isClientSide()) {
            if (surroundingBlock instanceof QubitBlock) {
                QubitBlock qubitBlock = QubitBlock.class.cast(surroundingBlock);
                double[] currentState = qubitBlock.stateVector.clone();
                qubitBlock.stateVector = currentState;
            }
        }

        return super.getStateForPlacement(placeContext);
    }

    /**
    double[] qstate = new double[2];

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {

        BlockPos positionClicked = pContext.getClickedPos();
        Player player = pContext.getPlayer();

        Block surroundingBlock = findClosest(pContext, positionClicked);

        if (surroundingBlock instanceof QubitBlock) {
            //qstate = ((QubitBlock) surroundingBlock).returnQState();
        } else if (surroundingBlock instanceof NotBlock) {
            //qstate = ((NotBlock) surroundingBlock).returnQState();
        } else if (surroundingBlock instanceof HadamardBlock) {
            //qstate = ((HadamardBlock) surroundingBlock).returnQState();
        }

        player.sendMessage(new TextComponent((Math.pow(qstate[0],2)*100) + "% 0  AND  " + (Math.pow(qstate[1],2)*100) + "% 1"), player.getUUID());

        returnQState();
        return super.getStateForPlacement(pContext);
    }

    public Block findClosest(BlockPlaceContext pContext, BlockPos positionClicked) {
        Block surroundingBlock = pContext.getLevel().getBlockState(positionClicked.north(1)).getBlock();

        int count = 2;
        while (surroundingBlock instanceof QuantumDust) {
            surroundingBlock = pContext.getLevel().getBlockState(positionClicked.north(count)).getBlock();
            count += 1;
            if (count > 10) {
                break;
            }
        }
        return surroundingBlock;
    }

    public double[] returnQState() {
        return qstate;
    }
    */
}

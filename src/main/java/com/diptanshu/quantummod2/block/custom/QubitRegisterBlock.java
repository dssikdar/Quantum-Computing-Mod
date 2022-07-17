package com.diptanshu.quantummod2.block.custom;

import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import java.util.HashMap;

public class QubitRegisterBlock extends Block {

    public QubitRegisterBlock(Properties properties) {
        super(properties);
    }

    //public Object[][] qRegisterState = {{1.0, 0.0, true}, {0.0, 0.0, false}, {0.0, 0.0, false}, {0.0, 0.0, false}, {0.0, 0.0, false}};

    public HashMap<String, Object[]> qRegisterState = new HashMap<String, Object[]>() {{
        put("up", new Object[]{0.0, 0.0, false});
        put("north", new Object[]{0.0, 0.0, false});
        put("south", new Object[]{0.0, 0.0, false});
        put("east", new Object[]{0.0, 0.0, false});
        put("west", new Object[]{0.0, 0.0, false});
    }};

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        Player player = pContext.getPlayer();
        Level pLevel = pContext.getLevel();
        qRegisterState.replace("up", new Object[]{1.0, 0.0, true});
        if (pLevel.isClientSide()) {
            player.sendMessage(new TextComponent("UP: " + qRegisterState.get("up")[0] + " |0>  &&  " + qRegisterState.get("up")[1] + " |1>"), player.getUUID());
        }
        return super.getStateForPlacement(pContext);
    }
}

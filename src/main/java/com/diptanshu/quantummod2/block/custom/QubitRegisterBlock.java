package com.diptanshu.quantummod2.block.custom;

import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import java.util.HashMap;
import java.util.Set;

public class QubitRegisterBlock extends Block {

    public QubitRegisterBlock(Properties properties) {
        super(properties);
    }

    //public Object[][] qRegisterState = {{1.0, 0.0, true}, {0.0, 0.0, false}, {0.0, 0.0, false}, {0.0, 0.0, false}, {0.0, 0.0, false}};

    public HashMap<String, double[]> qRegStateVector = new HashMap<String, double[]>() {{
        put("up", new double[]{0.0, 0.0});
        put("north", new double[]{0.0, 0.0});
        put("south", new double[]{0.0, 0.0});
        put("east", new double[]{0.0, 0.0});
        put("west", new double[]{0.0, 0.0});
    }};

    public double[] completeStateVector;

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        Player player = pContext.getPlayer();
        Level pLevel = pContext.getLevel();
        qRegStateVector.replace("up", new double[]{1.0, 0.0});
        if (pLevel.isClientSide()) {
            player.sendMessage(new TextComponent("UP: " + qRegStateVector.get("up")[0] + " |0>  &&  " +
                    qRegStateVector.get("up")[1] + " |1>"), player.getUUID());
        }
        printRegState(pLevel, player, qRegStateVector, "up");
        return super.getStateForPlacement(pContext);
    }

    public double[] tensorProduct (String dirList, HashMap<String, double[]> quantumRegister) {
        // Length of Quantum Register
        int length = quantumRegister.size();
        // Tensor Product (to be returned)
        double[] tensorProd = new double[2*length];
        // Initialization of Tensor Product to full-length '1' vector
        for (int i=0; i<length; i++) {
            tensorProd[i] = 1;
        }

        //Set<String> directions = quantumRegister.keySet();
        //for (int idx=0; idx<dirList.length(); idx++) {}

        return tensorProd;
    }

    public static void printRegState(Level level, Player player, HashMap<String, double[]> register, String direction) {
        if (level.isClientSide()) {
            player.sendMessage(new TextComponent(direction.toUpperCase() + register.get(direction)[0] +
                    " |0>  &&  " + register.get(direction)[1] + " |1>"), player.getUUID());
        }
    }
}

package com.diptanshu.quantummod2.block.custom;

import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

import static com.diptanshu.quantummod2.block.custom.QubitBlock.round;

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

    public ArrayList<String> listOfQubitFaces = new ArrayList<String>();

    public ArrayList<Double> tensorProd = new ArrayList<Double>();

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        Player player = pContext.getPlayer();
        Level pLevel = pContext.getLevel();
        listOfQubitFaces.clear();
        //qRegStateVector.replace("up", new double[]{1.0, 0.0});
        //printRegState(pLevel, player, qRegStateVector, "up");
        if (pLevel.isClientSide()) {
            player.sendMessage(new TextComponent("Empty Qubit Register"), player.getUUID());
        }
        return super.getStateForPlacement(pContext);
    }

    public void tensorProduct () {
        tensorProd.clear();

        ArrayList<Double> tpLeft = new ArrayList<Double>();

        // Number of qubits in the Quantum Register
        int numOfQubits = listOfQubitFaces.size();

        if (numOfQubits == 1) {
            tensorProd.add(qRegStateVector.get(listOfQubitFaces.get(0))[0]);
            tensorProd.add(qRegStateVector.get(listOfQubitFaces.get(0))[1]);
        }
        else {
            for (int i = 0; i < numOfQubits; i++) {
                if (i == 0) {
                    tpLeft.add(qRegStateVector.get(listOfQubitFaces.get(0))[0]);
                    tpLeft.add(qRegStateVector.get(listOfQubitFaces.get(0))[1]);
                } else {
                    int sizeOftpLeft = tpLeft.size();
                    tensorProd.clear();
                    for (int j = 0; j < sizeOftpLeft; j++) {
                        tensorProd.add(round(tpLeft.get(j) * qRegStateVector.get(listOfQubitFaces.get(i))[0], 3));
                        tensorProd.add(round(tpLeft.get(j) * qRegStateVector.get(listOfQubitFaces.get(i))[1], 3));
                    }
                    tpLeft.clear();
                    tpLeft.addAll(tensorProd);
                }
            }
        }

    }

    public static void printRegState(Level level, Player player, HashMap<String, double[]> register, String direction) {
        if (level.isClientSide()) {
            player.sendMessage(new TextComponent(direction.toUpperCase() + register.get(direction)[0] +
                    " |0> + " + register.get(direction)[1] + " |1>"), player.getUUID());
        }
    }
}

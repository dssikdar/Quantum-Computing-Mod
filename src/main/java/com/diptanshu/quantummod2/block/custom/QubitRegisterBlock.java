package com.diptanshu.quantummod2.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

import static com.diptanshu.quantummod2.block.custom.QubitBlock.round;

public class QubitRegisterBlock extends Block implements QregReferencer {

    public QubitRegisterBlock(Properties properties) {
        super(properties);
    }

    public static final double[] voidState = {0.0D, 0.0D};
    public static final double[] defaultState = {1.0D, 0.0D};

    // Create a Java hashmap to store all the amplitudes for all qubits in the register
    public static HashMap<String, double[]> qRegStateVector = new HashMap<String, double[]>();

    // Create a Java ArrayList to store order of qubits allocated in the register
    public static ArrayList<String> listOfQubitFaces = new ArrayList<String>();

    // Create a Java ArrayList that stores the tensor product for the quantum register
    public static ArrayList<Double> tensorProd = new ArrayList<Double>();

    /** Purpose: Upon placing the QubitRegisterBlock in the MC environment, initialize qRegStateVector with
     *  initial values to be initialized properly when qubit is allocated. Also clears list of any previously
     *  allocated qubits
     * @param pContext
     * @return BlockState
     */
    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        Player player = pContext.getPlayer();
        Level pLevel = pContext.getLevel();

        // Initializing the Quantum Register Hashmap
        qRegStateVector.put("up", voidState);
        qRegStateVector.put("north", voidState);
        qRegStateVector.put("south", voidState);
        qRegStateVector.put("east", voidState);
        qRegStateVector.put("west", voidState);

        // Clear the list of allocated qubits
        listOfQubitFaces.clear();

        if (pLevel.isClientSide()) {
            player.sendMessage(new TextComponent("Empty Qubit Register"), player.getUUID());
        }
        return super.getStateForPlacement(pContext);
    }

    public static void addToListOfQubitFaces(String face) {
        listOfQubitFaces.add(face);
    }
    public static ArrayList<String> getListOfQubitFaces() {
        return listOfQubitFaces;
    }

    /** Purpose: Calculates Tensor Product of the Quantum Register
     * @param
     * @return void
     */
    public static void setTensorProduct (ArrayList<Double> tensorProd) {
        tensorProd.clear();

        // Temporary ArrayList to store intermediate tensor products
        ArrayList<Double> tpLeft = new ArrayList<Double>();

        // Number of qubits in the Quantum Register
        int numOfQubits = listOfQubitFaces.size();

        // Add first quantum state to the tensorProd
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

    public static ArrayList<Double> getTensorProduct() {
        return tensorProd;
    }

    public static void printRegisterState(Level level, Player player, HashMap<String, double[]> register, String direction) {
        if (level.isClientSide()) {
            player.sendMessage(new TextComponent(direction.toUpperCase() + register.get(direction)[0] +
                    " |0> + " + register.get(direction)[1] + " |1>"), player.getUUID());
        }
    }

    public static void setRegisterState(String blockFace, double[] newvector){
        qRegStateVector.put(blockFace, newvector);
    }

    public static double[] getRegisterState(String blockFace){
        if(!qRegStateVector.containsKey(blockFace)){
            qRegStateVector.put(blockFace, defaultState.clone());
        }
        return qRegStateVector.get(blockFace);
    }

    @Override
    public BlockPos supplyRegister (BlockPos pos) {
        return pos;
    }
}

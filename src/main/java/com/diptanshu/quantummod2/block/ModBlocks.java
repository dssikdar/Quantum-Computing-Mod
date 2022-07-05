package com.diptanshu.quantummod2.block;

import com.diptanshu.quantummod2.QuantumMod2;
import com.diptanshu.quantummod2.block.custom.HadamardBlock;
import com.diptanshu.quantummod2.block.custom.NotBlock;
import com.diptanshu.quantummod2.block.custom.QuantumDust;
import com.diptanshu.quantummod2.block.custom.QubitBlock;
import com.diptanshu.quantummod2.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, QuantumMod2.MOD_ID);

    /**
    public static final RegistryObject<Block> QUBIT_BLOCK = registerBlock("qubit_block",
            () -> new QubitBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(6f).requiresCorrectToolForDrops()),CreativeModeTab.TAB_MISC);
     */

    public static final RegistryObject<Block> QUBIT_BLOCK = registerBlock("qubit_block",
            () -> new QubitBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(6f).requiresCorrectToolForDrops()),CreativeModeTab.TAB_MISC);

    /**
    public static final RegistryObject<Block> QUANTUM_DUST = registerBlock("quantum_dust",
            () -> new QuantumDust(BlockBehaviour.Properties.of(Material.POWDER_SNOW)
                    .strength(3f).requiresCorrectToolForDrops()),CreativeModeTab.TAB_MISC);
     */

    public static final RegistryObject<Block> NOT_BLOCK = registerBlock("not_block",
            () -> new NotBlock(false,BlockBehaviour.Properties.of(Material.STONE)
                    .strength(6f).requiresCorrectToolForDrops()),CreativeModeTab.TAB_MISC);

    public static final RegistryObject<Block> HADAMARD_BLOCK = registerBlock("hadamard_block",
            () -> new HadamardBlock(false, BlockBehaviour.Properties.of(Material.STONE)
                    .strength(6f).requiresCorrectToolForDrops()),CreativeModeTab.TAB_MISC);


    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }

    private static <T extends Block>RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().tab(tab)));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
